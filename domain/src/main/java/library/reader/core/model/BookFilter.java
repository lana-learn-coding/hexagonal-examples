package library.reader.core.model;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class BookFilter {
    private boolean isBorrowed = true;

    private BookFilter() {
    }

    public static BookFilter notBorrowed() {
        return new BookFilter().isBorrowed(false);
    }
}
