package library.reader.core.model.action;

import lombok.Data;

import java.util.List;

@Data
public class BorrowBookAction {
    private Integer readerId;

    private List<Integer> bookToBorrowIds;

    private boolean ignoreBorrowedBook;
}
