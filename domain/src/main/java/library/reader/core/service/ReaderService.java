package library.reader.core.service;

import library.reader.core.model.action.BorrowBookAction;
import library.reader.core.model.action.ReturnBorrowedBookAction;

import java.util.List;

public interface ReaderService {
    List<Integer> borrowBook(BorrowBookAction borrowBookAction);

    List<Integer> returnBorrowedBook(ReturnBorrowedBookAction returnBorrowedBookAction);
}
