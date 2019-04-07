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

    @ContainedIn
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    @ContainedIn
    @ManyToMany(mappedBy = "clients")
    private List<Board> client;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
