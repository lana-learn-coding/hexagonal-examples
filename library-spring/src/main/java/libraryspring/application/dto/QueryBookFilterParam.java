package libraryspring.application.dto;

import lombok.Data;

@Data
public class QueryBookFilterParam {
    private Integer borrowerId;

    private String name;
}
