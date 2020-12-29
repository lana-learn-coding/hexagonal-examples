package library.book.core.port.outgoing;

import library.book.core.model.Book;
import library.book.core.model.action.QueryBookAction;

import java.util.List;

public interface BookRepo {
    void save(Book book);

    List<Book> findAllBooks(QueryBookAction queryBookAction);
}
