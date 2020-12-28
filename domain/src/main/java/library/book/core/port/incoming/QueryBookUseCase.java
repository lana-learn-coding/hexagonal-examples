package library.book.core.port.incoming;

import library.book.core.model.Book;
import library.book.core.model.action.BookQueryAction;

import java.util.List;

public interface QueryBookUseCase {
    List<Book> getAllBooks(BookQueryAction bookQueryAction);

}
