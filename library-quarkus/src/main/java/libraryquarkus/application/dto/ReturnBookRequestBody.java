package libraryquarkus.application.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ReturnBookRequestBody {
    @NotNull
    @NotEmpty
    private List<Integer> bookIds;
}
