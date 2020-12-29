package library.reader.core.port.outgoing;

import library.reader.core.model.Reader;

import java.util.Optional;

public interface ReaderRepo {
    void save(Reader reader);

    Optional<Reader> findById(Integer id);

    boolean existByEmail(String email);
}
