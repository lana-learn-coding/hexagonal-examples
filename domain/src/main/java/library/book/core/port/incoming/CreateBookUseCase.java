package library.book.core.port.incoming;

import library.book.core.model.Book;
import library.book.core.model.action.CreateBookAction;

public interface CreateBookUseCase {
    Book createBook(CreateBookAction action);
}
