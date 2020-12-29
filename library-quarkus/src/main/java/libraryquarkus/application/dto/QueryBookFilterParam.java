package libraryquarkus.application.dto;

import lombok.Data;

import javax.ws.rs.QueryParam;

@Data
public class QueryBookFilterParam {
    @QueryParam("borrower")
    private Integer borrowerId;

    @QueryParam("name")
    private String name;
}
