package library.reader.core.model.action;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateReaderAction {
    @NotBlank
    private String name;

    @NotBlank
    private String email;
}
