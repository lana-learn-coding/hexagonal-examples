package libraryquarkus.application;

import libraryquarkus.application.dto.BorrowBookRequestBody;
import libraryquarkus.application.dto.ReturnBookRequestBody;
import library.reader.core.model.Reader;
import library.reader.core.model.action.BorrowBookAction;
import library.reader.core.model.action.CreateReaderAction;
import library.reader.core.model.action.ReturnBorrowedBookAction;
import library.reader.core.port.incoming.BorrowBookUseCase;
import library.reader.core.port.incoming.CreateReaderUseCase;
import library.reader.core.port.incoming.ReturnBookUseCase;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/readers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReaderController {

    @Inject
    CreateReaderUseCase createReaderUseCase;

    @Inject
    ReturnBookUseCase returnBookUseCase;

    @Inject
    BorrowBookUseCase borrowBookUseCase;

    @Transactional
    @POST
    public Response createReader(CreateReaderAction readerPayload) {
        Reader reader = createReaderUseCase.createReader(readerPayload);
        return Response.ok(reader).build();
    }

    @Transactional
    @PUT
    @Path("/{id}/borrow-books")
    public Response borrowBook(@PathParam("id") Integer id, BorrowBookRequestBody borrowBookRequestBody) {
        BorrowBookAction borrowBookAction = new BorrowBookAction();
        borrowBookAction.setBookToBorrowIds(borrowBookRequestBody.getBookIds());
        borrowBookAction.setReaderId(id);
        borrowBookAction.setIgnoreBorrowedBook(borrowBookRequestBody.isIgnoreBorrowedBook());
        List<Integer> borrowedIds = borrowBookUseCase.borrowBook(borrowBookAction);
        return Response.ok(borrowedIds).build();
    }

    @Transactional
    @PUT
    @Path("/{id}/return-books")
    public Response returnBook(@PathParam("id") Integer id, ReturnBookRequestBody returnBookRequestBody) {
        ReturnBorrowedBookAction returnBorrowedBookAction = new ReturnBorrowedBookAction();
        returnBorrowedBookAction.setBorrowedBookIds(returnBookRequestBody.getBookIds());
        returnBorrowedBookAction.setReaderId(id);
        List<Integer> borrowedIds = returnBookUseCase.returnBorrowedBook(returnBorrowedBookAction);
        return Response.ok(borrowedIds).build();
    }
}