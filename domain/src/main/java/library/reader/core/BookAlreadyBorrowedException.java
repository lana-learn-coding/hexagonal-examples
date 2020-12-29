package library.reader.core;

public class BookAlreadyBorrowedException extends IllegalArgumentException {
    public BookAlreadyBorrowedException() {
        super("Book already borrowed or not exist!");
    }
}
