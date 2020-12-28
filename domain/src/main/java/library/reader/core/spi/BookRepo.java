package library.reader.core.spi;

import library.reader.core.model.BookFilter;

import java.util.List;

public interface BookRepo {
    List<Integer> findBookIds(BookFilter filter);
}
