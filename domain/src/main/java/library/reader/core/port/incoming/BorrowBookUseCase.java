package library.reader.core.port.incoming;

import library.reader.core.model.action.BorrowBookAction;

import java.util.List;

public interface BorrowBookUseCase {
    List<Integer> borrowBook(BorrowBookAction borrowBookAction);
}
