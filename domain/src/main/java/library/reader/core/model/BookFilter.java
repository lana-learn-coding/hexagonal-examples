package library.reader.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
public class BookFilter {
    private boolean containBorrowedBook = true;

    private List<Integer> bookIds;

    private BookFilter() {
    }

    public static BookFilter notBorrowed() {
        return new BookFilter().containBorrowedBook(false);
    }
}
