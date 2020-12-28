package library.book.core.model.action;

import lombok.Data;

@Data
public class BookQueryAction {
    private Integer borrowerId;

    private String name;
}
