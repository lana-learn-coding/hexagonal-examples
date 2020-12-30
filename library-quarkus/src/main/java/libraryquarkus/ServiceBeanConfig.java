package libraryquarkus;

import library.book.core.BookService;
import library.reader.core.ReaderService;
import library.reader.core.port.outgoing.BookRepo;
import library.reader.core.port.outgoing.ReaderRepo;

import javax.enterprise.inject.Default;

public class ServiceBeanConfig {
    @Default
    public BookService bookService(library.book.core.port.outgoing.BookRepo bookRepo) {
        return new BookService(bookRepo);
    }

    @Default
    public ReaderService readerService(BookRepo bookRepo, ReaderRepo readerRepo) {
        return new ReaderService(bookRepo, readerRepo);
    }
}
