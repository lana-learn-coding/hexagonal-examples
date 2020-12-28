package library.reader.core.model.action;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ReturnBorrowedBookAction {
    @NotNull
    private Integer readerId;

    @NotEmpty
    private List<String> borrowedBookIds;
}
