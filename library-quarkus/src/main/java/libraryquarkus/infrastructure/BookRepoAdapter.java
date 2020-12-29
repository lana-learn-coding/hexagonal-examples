package libraryquarkus.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import library.book.core.model.Book;
import library.book.core.model.action.QueryBookAction;
import library.book.core.port.outgoing.BookRepo;
import library.reader.core.model.BookFilter;
import libraryquarkus.infrastructure.entity.BookEntity;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookRepoAdapter implements BookRepo, library.reader.core.port.outgoing.BookRepo,
    PanacheRepositoryBase<BookEntity, Integer> {
    @Override
    public void save(Book book) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName(book.getName());
        bookEntity.setCreatedBy(book.getCreatedBy());
        persist(bookEntity);
        flush();

        book.setId(bookEntity.getId());
    }

    @Override
    public List<Book> findAllBooks(QueryBookAction queryBookAction) {
        return this.findAll().stream().map(entity -> {
            Book book = new Book();
            book.setId(entity.getId());
            book.setName(entity.getName());
            book.setCreatedBy(entity.getCreatedBy());

            if (entity.getBorrower() != null) {
                book.setBorrowerEmail(entity.getBorrower().getEmail());
                book.setBorrowerId(entity.getBorrower().getId());
            }

            return book;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Integer> findBookIds(BookFilter filter) {
        return findBookByFilter(filter)
            .stream()
            .map(BookEntity::getId)
            .collect(Collectors.toList());
    }

    private PanacheQuery<BookEntity> findBookByFilter(BookFilter filter) {
        String query = "1 = 1";
        if (!filter.containBorrowedBook()) {
             query +=" AND borrower is null";
        }
        if (filter.bookIds() != null){
            query +=" AND id in ?1";
        }
        return query.equals("1 = 1")
            ? findAll()
            : find(query, filter.bookIds());
    }
}
