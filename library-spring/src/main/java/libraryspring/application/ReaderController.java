package libraryspring.application;

import library.reader.core.model.Reader;
import library.reader.core.model.action.BorrowBookAction;
import library.reader.core.model.action.CreateReaderAction;
import library.reader.core.model.action.ReturnBorrowedBookAction;
import library.reader.core.port.incoming.BorrowBookUseCase;
import library.reader.core.port.incoming.CreateReaderUseCase;
import library.reader.core.port.incoming.ReturnBookUseCase;
import libraryspring.application.dto.BorrowBookRequestBody;
import libraryspring.application.dto.ReturnBookRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/api/readers", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReaderController {

    private CreateReaderUseCase createReaderUseCase;

    private ReturnBookUseCase returnBookUseCase;

    private BorrowBookUseCase borrowBookUseCase;

    @Autowired
    public void setup(CreateReaderUseCase createReaderUseCase, ReturnBookUseCase returnBookUseCase,
                      BorrowBookUseCase borrowBookUseCase) {
        this.createReaderUseCase = createReaderUseCase;
        this.returnBookUseCase = returnBookUseCase;
        this.borrowBookUseCase = borrowBookUseCase;
    }

    @Transactional
    @PostMapping
    public Reader createReader(@RequestBody CreateReaderAction readerPayload) {
        return createReaderUseCase.createReader(readerPayload);
    }

    @Transactional
    @PutMapping("/{id}/borrow-books")
    public List<Integer> borrowBook(@PathVariable Integer id, @RequestBody BorrowBookRequestBody borrowBookRequestBody) {
        BorrowBookAction borrowBookAction = new BorrowBookAction();
        borrowBookAction.setBookToBorrowIds(borrowBookRequestBody.getBookIds());
        borrowBookAction.setReaderId(id);
        borrowBookAction.setIgnoreBorrowedBook(borrowBookRequestBody.isIgnoreBorrowedBook());
        return borrowBookUseCase.borrowBook(borrowBookAction);
    }

    @Transactional
    @PutMapping("/{id}/return-books")
    public List<Integer> returnBook(@PathVariable Integer id, @RequestBody ReturnBookRequestBody returnBookRequestBody) {
        ReturnBorrowedBookAction returnBorrowedBookAction = new ReturnBorrowedBookAction();
        returnBorrowedBookAction.setBorrowedBookIds(returnBookRequestBody.getBookIds());
        returnBorrowedBookAction.setReaderId(id);
        return returnBookUseCase.returnBorrowedBook(returnBorrowedBookAction);
    }
}