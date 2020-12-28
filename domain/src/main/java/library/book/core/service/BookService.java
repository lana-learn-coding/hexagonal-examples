package library.book.core.service;

import library.book.core.model.Book;
import library.book.core.model.action.BookCreationAction;
import library.book.core.model.action.BookQueryAction;

import java.util.List;

public interface BookService {
    Book createBook(BookCreationAction bookCreationAction);

    List<Book> getAllBooks(BookQueryAction bookQueryAction);
}
