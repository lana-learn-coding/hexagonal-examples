package library.reader.core.model;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class BookFilter {
    private boolean isBorrowed = true;

    private Integer borrower;

    private BookFilter() {
    }

    public static BookFilter notBorrowed() {
        return new BookFilter().isBorrowed(false);
    }

    public static BookFilter borrowedBy(Integer reader) {
        return new BookFilter().isBorrowed(false).borrower(reader);
    }
}
