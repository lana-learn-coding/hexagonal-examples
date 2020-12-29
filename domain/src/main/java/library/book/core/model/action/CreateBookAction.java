package library.book.core.model.action;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateBookAction {
    @NotBlank
    private String name;

    @NotBlank
    private String createdBy;
}
