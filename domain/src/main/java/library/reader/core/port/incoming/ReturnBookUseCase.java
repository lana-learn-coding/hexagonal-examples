package library.reader.core.port.incoming;

import library.reader.core.model.action.ReturnBorrowedBookAction;

import java.util.List;

public interface ReturnBookUseCase {
    List<Integer> returnBorrowedBook(ReturnBorrowedBookAction returnBorrowedBookAction);
}
