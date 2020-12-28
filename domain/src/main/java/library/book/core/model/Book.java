package library.book.core.model;

import lombok.Data;

@Data
public class Book {
    private Integer id;

    private String name;

    private Integer borrowerId;

    private String borrowerEmail;

    private String createdBy;

    public boolean isBorrowed() {
        return borrowerId != null;
    }
}
