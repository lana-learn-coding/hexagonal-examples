package library.book.core;

import library.book.core.model.Book;
import library.book.core.model.action.BookCreationAction;
import library.book.core.model.action.BookQueryAction;
import library.book.core.service.BookService;
import library.book.core.spi.BookRepo;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class BookServiceFacade implements BookService {
    private BookRepo bookRepo;

    @Override
    public Book createBook(BookCreationAction bookCreationAction) {
        Book book = new Book();
        book.setCreatedBy(bookCreationAction.getCreatedBy());
        book.setName(bookCreationAction.getName());
        bookRepo.save(book);
        return book;
    }

    @Override
    public List<Book> getAllBooks(BookQueryAction bookQueryAction) {
        return bookRepo.findAllBooks(bookQueryAction);
    }
}
