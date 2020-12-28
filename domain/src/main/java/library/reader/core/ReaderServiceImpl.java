package library.reader.core;

import library.reader.core.model.BookFilter;
import library.reader.core.model.Reader;
import library.reader.core.model.action.BorrowBookAction;
import library.reader.core.model.action.ReturnBorrowedBookAction;
import library.reader.core.service.ReaderService;
import library.reader.core.spi.BookRepo;
import library.reader.core.spi.ReaderRepo;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ReaderServiceImpl implements ReaderService {
    @Inject
    private BookRepo bookRepo;

    @Inject
    private ReaderRepo readerRepo;

    @Override
    public List<Integer> borrowBook(BorrowBookAction borrowBookAction) {
        Reader reader = readerRepo.findById(borrowBookAction.getReaderId())
            .orElseThrow(() -> new ReaderNotFoundException(borrowBookAction.getReaderId()));

        BookFilter filter = BookFilter.notBorrowed();
        List<Integer> bookIds = bookRepo.findBookIds(filter);
        if (!borrowBookAction.isIgnoreBorrowedBook() && bookIds.size() < borrowBookAction.getBookToBorrowIds().size()) {
            throw new BookAlreadyBorrowedException();
        }

        reader.getBorrowedBookIds().addAll(bookIds);
        readerRepo.save(reader);
        return bookIds;
    }

    @Override
    public List<Integer> returnBorrowedBook(ReturnBorrowedBookAction returnBorrowedBookAction) {
        Reader reader = readerRepo.findById(returnBorrowedBookAction.getReaderId())
            .orElseThrow(() -> new ReaderNotFoundException(returnBorrowedBookAction.getReaderId()));

        BookFilter filter = BookFilter.borrowedBy(returnBorrowedBookAction.getReaderId());
        List<Integer> borrowedBookIds = bookRepo.findBookIds(filter);
        if (borrowedBookIds.isEmpty()) {
            return new ArrayList<>();
        }

        reader.getBorrowedBookIds().removeAll(borrowedBookIds);
        readerRepo.save(reader);
        return borrowedBookIds;
    }
}
