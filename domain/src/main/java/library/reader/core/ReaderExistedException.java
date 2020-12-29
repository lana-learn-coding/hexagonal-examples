package library.reader.core;

public class ReaderExistedException extends IllegalArgumentException {
    ReaderExistedException() {
        super("Reader already existed");
    }
}
