package library.reader.core;

import library.reader.core.model.BookFilter;
import library.reader.core.model.Reader;
import library.reader.core.model.action.BorrowBookAction;
import library.reader.core.model.action.CreateReaderAction;
import library.reader.core.model.action.ReturnBorrowedBookAction;
import library.reader.core.port.incoming.BorrowBookUseCase;
import library.reader.core.port.incoming.CreateReaderUseCase;
import library.reader.core.port.incoming.ReturnBookUseCase;
import library.reader.core.port.outgoing.BookRepo;
import library.reader.core.port.outgoing.ReaderRepo;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ReaderService implements BorrowBookUseCase, ReturnBookUseCase, CreateReaderUseCase {
    private BookRepo bookRepo;

    private ReaderRepo readerRepo;

    @Inject
    public void setup(BookRepo bookRepo, ReaderRepo readerRepo) {
        this.bookRepo = bookRepo;
        this.readerRepo = readerRepo;
    }

    @Override
    public List<Integer> borrowBook(BorrowBookAction borrowBookAction) {
        Reader reader = readerRepo.findOne(borrowBookAction.getReaderId())
            .orElseThrow(() -> new ReaderNotFoundException(borrowBookAction.getReaderId()));

        BookFilter filter = BookFilter.notBorrowed();
        List<Integer> bookIds = bookRepo.findBookIds(filter);
        if (!borrowBookAction.isIgnoreBorrowedBook() && bookIds.size() < borrowBookAction.getBookToBorrowIds().size()) {
            throw new BookAlreadyBorrowedException();
        }

        reader.getBorrowedBookIds().addAll(bookIds);
        readerRepo.updateBorrowedBookList(reader);
        return bookIds;
    }

    @Override
    public List<Integer> returnBorrowedBook(ReturnBorrowedBookAction returnBorrowedBookAction) {
        Reader reader = readerRepo.findOne(returnBorrowedBookAction.getReaderId())
            .orElseThrow(() -> new ReaderNotFoundException(returnBorrowedBookAction.getReaderId()));

        BookFilter filter = BookFilter.borrowedBy(returnBorrowedBookAction.getReaderId());
        List<Integer> borrowedBookIds = bookRepo.findBookIds(filter);
        if (borrowedBookIds.isEmpty()) {
            return new ArrayList<>();
        }

        reader.getBorrowedBookIds().removeAll(borrowedBookIds);
        readerRepo.updateBorrowedBookList(reader);
        return borrowedBookIds;
    }

    @Override
    public Reader createReader(CreateReaderAction action) {
        String email = action.getEmail();
        if (readerRepo.existByEmail(email)) {
            throw new ReaderExistedException();
        }
        Reader reader = new Reader();
        reader.setEmail(email);
        reader.setName(action.getName());
        readerRepo.save(reader);
        return reader;
    }
}
