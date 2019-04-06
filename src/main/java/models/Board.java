package models;

import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "board")
public class Board implements BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardID;
    @Field(termVector = TermVector.YES)
    private Date lastRepair;
    @Field(termVector = TermVector.YES)
    private String jetType;
    @Field(termVector = TermVector.YES)
    private int freeSeat;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlightInfo> flightInfos;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    @Override
    public String toString() {
        return jetType + " #" + boardID;
    }

    public void addFlightInformation(FlightInfo flightInfo) {
        flightInfo.setBoard(this);
        flightInfos.add(flightInfo);
    }

    public void removeFlightInformation(FlightInfo flightInfo) {
        flightInfos.remove(flightInfo);
    }

    public void addTicket(Ticket ticket) {
        ticket.setBoard(this);
        tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }
}
