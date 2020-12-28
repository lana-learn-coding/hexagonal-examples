package libraryspring.application;

import library.book.core.model.Book;
import library.book.core.model.action.CreateBookAction;
import library.book.core.model.action.QueryBookAction;
import library.book.core.port.incoming.CreateBookUseCase;
import library.book.core.port.incoming.QueryBookUseCase;
import libraryspring.application.dto.QueryBookFilterParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
    private CreateBookUseCase createBookUseCase;

    private QueryBookUseCase queryBookUseCase;

    @Autowired
    public void setup(QueryBookUseCase queryBookUseCase, CreateBookUseCase createBookUseCase) {
        this.queryBookUseCase = queryBookUseCase;
        this.createBookUseCase = createBookUseCase;
    }

    @PostMapping
    @Transactional
    public Book createBook(CreateBookAction createBookAction) {
        return createBookUseCase.createBook(createBookAction);
    }

    @GetMapping
    public List<Book> queryBook(QueryBookFilterParam queryBookFilterParam) {
        QueryBookAction queryAction = new QueryBookAction();
        queryBookFilterParam.setName(queryBookFilterParam.getName());
        queryBookFilterParam.setBorrowerId(queryBookFilterParam.getBorrowerId());
        return queryBookUseCase.getAllBooks(queryAction);
    }
}
