package models;

import lombok.Data;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Indexed
@Table (name = "client")

public class Client implements BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;

    @Field
    private String firstName;

    @Field
    private String lastName;

    @Field
    private String passportID;

    @IndexedEmbedded
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    @ContainedIn
    @ManyToMany(mappedBy = "fromBoardToClient")
    private List<Board> fromBoardToClient;

    @IndexedEmbedded
    @ManyToMany
    @JoinTable(
            name = "ticket",
            joinColumns = {@JoinColumn(name = "client")},
            inverseJoinColumns = {@JoinColumn(name = "board")}
    )
    private List<Board> fromClientToBoard;


    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
