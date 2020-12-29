package libraryquarkus.application;

import libraryquarkus.application.dto.QueryBookFilterParam;
import library.book.core.model.Book;
import library.book.core.model.action.CreateBookAction;
import library.book.core.model.action.QueryBookAction;
import library.book.core.port.incoming.CreateBookUseCase;
import library.book.core.port.incoming.QueryBookUseCase;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookController {
    @Inject
    CreateBookUseCase createBookUseCase;

    @Inject
    QueryBookUseCase queryBookUseCase;

    @Transactional
    @POST
    public Response createBook(CreateBookAction createBookAction) {
        Book book = createBookUseCase.createBook(createBookAction);
        return Response.ok(book).build();
    }

    @GET
    public Response queryBook(@BeanParam QueryBookFilterParam queryBookFilterParam) {
        QueryBookAction queryAction = new QueryBookAction();
        queryBookFilterParam.setName(queryBookFilterParam.getName());
        queryBookFilterParam.setBorrowerId(queryBookFilterParam.getBorrowerId());
        List<Book> books = queryBookUseCase.getAllBooks(queryAction);
        return Response.ok(books).build();
    }
}
