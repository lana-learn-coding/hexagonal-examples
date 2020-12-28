package libraryspring.infrastructure;

import library.reader.core.model.Reader;
import library.reader.core.port.outgoing.ReaderRepo;
import libraryspring.infrastructure.entity.BookEntity;
import libraryspring.infrastructure.entity.ReaderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ReaderRepoAdapter implements ReaderRepo {

    @Autowired
    private BookRepoAdapter bookRepo;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void updateBorrowedBookList(Reader reader) {
        bookRepo.addMissingBookToBorrowList(reader.getId(), reader.getBorrowedBookIds());
        bookRepo.removeBookNotExistInBorrowList(reader.getId(), reader.getBorrowedBookIds());
    }

    @Override
    public void save(Reader reader) {
        ReaderEntity readerEntity = new ReaderEntity();
        readerEntity.setEmail(reader.getEmail());
        readerEntity.setName(reader.getName());
        entityManager.persist(readerEntity);
        entityManager.flush();

        reader.setId(readerEntity.getId());
    }

    @Override
    public Optional<Reader> findOne(Integer id) {
        return findById(id).map(entity -> {
            Reader reader = new Reader();
            reader.setId(entity.getId());
            reader.setEmail(entity.getEmail());
            reader.setName(entity.getName());
            reader.setBorrowedBookIds(entity.getBorrowedBooks().stream().map(BookEntity::getId).collect(Collectors.toList()));
            return reader;
        });
    }

    @Override
    public boolean existByEmail(String email) {
        return false;
    }

    private Optional<ReaderEntity> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(ReaderEntity.class, id));
    }
}
