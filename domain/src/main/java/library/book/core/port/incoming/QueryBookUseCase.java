package library.book.core.port.incoming;

import library.book.core.model.Book;
import library.book.core.model.action.QueryBookAction;

import java.util.List;

public interface QueryBookUseCase {
    List<Book> getAllBooks(QueryBookAction queryBookAction);

}
