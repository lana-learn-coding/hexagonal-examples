package libraryspring;

import library.book.core.BookService;
import library.reader.core.ReaderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfig {

    @Bean
    public BookService bookService() {
        return new BookService();
    }

    @Bean
    public ReaderService readerService() {
        return new ReaderService();
    }
}
