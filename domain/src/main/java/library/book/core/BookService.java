package library.book.core;

import library.book.core.model.Book;
import library.book.core.model.action.CreateBookAction;
import library.book.core.model.action.QueryBookAction;
import library.book.core.port.incoming.CreateBookUseCase;
import library.book.core.port.incoming.QueryBookUseCase;
import library.book.core.port.outgoing.BookRepo;

import java.util.List;

public class BookService implements CreateBookUseCase, QueryBookUseCase {
    private final BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public Book createBook(CreateBookAction bookCreationAction) {
        Book book = new Book();
        book.setCreatedBy(bookCreationAction.getCreatedBy());
        book.setName(bookCreationAction.getName());
        bookRepo.save(book);
        return book;
    }

    @Override
    public List<Book> getAllBooks(QueryBookAction queryBookAction) {
        return bookRepo.findAllBooks(queryBookAction);
    }
}
