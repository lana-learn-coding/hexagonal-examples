package library.book.core.port.outgoing;

import library.book.core.model.Book;
import library.book.core.model.action.BookQueryAction;

import java.util.List;

public interface BookRepo {
    void save(Book book);

    List<Book> findAllBooks(BookQueryAction bookQueryAction);
}