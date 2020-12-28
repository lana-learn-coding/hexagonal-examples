package libraryspring.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class ReaderEntity {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "borrower")
    private Set<BookEntity> borrowedBooks;
}
