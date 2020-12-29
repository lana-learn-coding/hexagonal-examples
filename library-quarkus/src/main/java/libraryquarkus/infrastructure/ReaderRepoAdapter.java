package libraryquarkus.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import library.reader.core.model.Reader;
import library.reader.core.port.outgoing.ReaderRepo;
import libraryquarkus.infrastructure.entity.BookEntity;
import libraryquarkus.infrastructure.entity.ReaderEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReaderRepoAdapter implements ReaderRepo, PanacheRepositoryBase<ReaderEntity, Integer> {
    @Inject
    BookRepoAdapter bookRepoAdapter;

    @Override
    public void save(Reader reader) {
        ReaderEntity readerEntity = new ReaderEntity();
        readerEntity.setName(reader.getName());
        readerEntity.setEmail(reader.getEmail());
        persist(readerEntity);
        flush();

        reader.setId(readerEntity.getId());
    }

    @Override
    public void updateBorrowedBookList(Reader reader) {
        bookRepoAdapter.update("borrower = null WHERE borrower.id = ?1 AND id not in ?2", reader.getId(), reader.getBorrowedBookIds());

        bookRepoAdapter.update("borrower_id = ?1 WHERE borrower is null AND id in ?2", reader.getId(), reader.getBorrowedBookIds());
    }

    @Override
    public Optional<Reader> findOne(Integer id) {
        return findByIdOptional(id).map(entity -> {
            Reader reader = new Reader();
            reader.setId(entity.getId());
            reader.setName(entity.getName());
            reader.setBorrowedBookIds(entity.getBorrowedBooks().stream().map(BookEntity::getId).collect(Collectors.toList()));
            reader.setEmail(entity.getEmail());
            return reader;
        });
    }

    @Override
    public boolean existByEmail(String email) {
        return find("email = ?1", email).firstResultOptional().isPresent();
    }
}
