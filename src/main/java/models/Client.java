package models;

import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table (name = "client")

public class Client implements BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;
    @Field(termVector = TermVector.YES)
    private String firstName;
    @Field(termVector = TermVector.YES)
    private String lastName;
    @Field(termVector = TermVector.YES)
    private String passportID;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public void addTicket(Ticket ticket) {
        ticket.setClient(this);
        tickets.add(ticket);
    }


    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }
}
