package libraryspring.infrastructure;

import library.book.core.model.Book;
import library.book.core.model.action.QueryBookAction;
import library.reader.core.model.BookFilter;
import libraryspring.infrastructure.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface BookRepoAdapter extends library.reader.core.port.outgoing.BookRepo,
    library.book.core.port.outgoing.BookRepo, JpaRepository<BookEntity, Integer> {

    @Query("select b from BookEntity b where (:containBorrowed is false or b.borrower is null) " +
        "and (:bookIds is null or b.id in :bookIds)")
    List<BookEntity> findBookByFilter(@Param("bookIds") List<Integer> bookIds,
                                      @Param("containBorrowed") boolean containBorrowed);

    @Modifying
    @Query("update BookEntity set borrower_id = ?1 where borrower is null AND id in ?2")
    void addMissingBookToBorrowList(Integer borrowerId, List<Integer> borrowList);

    @Modifying
    @Query("update BookEntity set borrower = null WHERE borrower.id = ?1 AND id not in ?2")
    void removeBookNotExistInBorrowList(Integer borrowerId, List<Integer> borrowList);

    @Override
    default void save(Book book) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName(book.getName());
        bookEntity.setCreatedBy(book.getCreatedBy());
        save(bookEntity);

        book.setId(bookEntity.getId());
    }

    @Override
    default List<Book> findAllBooks(QueryBookAction queryBookAction) {
        List<Book> books = new ArrayList<>();
        findAll().forEach(entity -> {
            Book book = new Book();
            book.setId(entity.getId());
            book.setName(entity.getName());
            book.setCreatedBy(entity.getCreatedBy());

            if (entity.getBorrower() != null) {
                book.setBorrowerEmail(entity.getBorrower().getEmail());
                book.setBorrowerId(entity.getBorrower().getId());
            }

            books.add(book);
        });
        return books;
    }

    @Override
    default List<Integer> findBookIds(BookFilter filter) {
        return findBookByFilter(filter.bookIds(), filter.containBorrowedBook()).
            stream().map(BookEntity::getId).collect(Collectors.toList());
    }
}
