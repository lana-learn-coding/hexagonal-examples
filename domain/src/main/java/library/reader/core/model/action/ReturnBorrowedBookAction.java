package library.reader.core.model.action;

import lombok.Data;

import java.util.List;

@Data
public class ReturnBorrowedBookAction {
    private Integer readerId;

    private List<String> borrowedBookIds;
}
