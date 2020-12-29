package library.reader.core.port.incoming;

import library.reader.core.model.Reader;
import library.reader.core.model.action.CreateReaderAction;

public interface CreateReaderUseCase {
    Reader createReader(CreateReaderAction action);
}
