package library.book.core.port.incoming;

import library.book.core.model.Book;
import library.book.core.model.action.BookCreationAction;

public interface CreateBookUseCase {
    Book createBook(BookCreationAction action);
}
