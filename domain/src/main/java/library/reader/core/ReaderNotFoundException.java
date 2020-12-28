package library.reader.core;

import java.util.NoSuchElementException;

public class ReaderNotFoundException extends NoSuchElementException {
    public ReaderNotFoundException(Integer id) {
        super("Reader with id of " + id + " not found");
    }
}
