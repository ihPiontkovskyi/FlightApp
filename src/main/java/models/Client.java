package models;

import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.TermVector;

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

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
