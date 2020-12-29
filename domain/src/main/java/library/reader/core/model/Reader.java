package library.reader.core.model;

import lombok.Data;

import java.util.List;

@Data
public class Reader {
    private Integer id;

    private String name;

    private String email;

    private List<Integer> borrowedBookIds;
}
