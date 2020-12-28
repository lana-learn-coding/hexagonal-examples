package library.book.core.model.action;

import lombok.Data;

@Data
public class BookCreationAction {
    private String name;

    private String createdBy;
}
