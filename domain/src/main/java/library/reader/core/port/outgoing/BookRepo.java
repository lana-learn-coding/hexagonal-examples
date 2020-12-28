package library.reader.core.port.outgoing;

import library.reader.core.model.BookFilter;

import java.util.List;

public interface BookRepo {
    List<Integer> findBookIds(BookFilter filter);
}
