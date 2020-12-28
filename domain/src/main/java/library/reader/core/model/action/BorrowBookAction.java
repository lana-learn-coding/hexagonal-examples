package library.reader.core.model.action;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BorrowBookAction {
    @NotNull
    private Integer readerId;

    @NotNull
    @NotEmpty
    private List<Integer> bookToBorrowIds;

    private boolean ignoreBorrowedBook;
}
