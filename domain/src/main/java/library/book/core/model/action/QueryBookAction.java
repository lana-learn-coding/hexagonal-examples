package library.book.core.model.action;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class QueryBookAction {
    @NotNull
    private Integer borrowerId;

    @NotBlank
    private String name;
}
