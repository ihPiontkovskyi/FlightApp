package models;

import lombok.Data;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
@Entity
@Indexed
@Table(name = "flight")
public class Flight implements BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightID;

    @Field
    private Time duration;

    @Field
    @DateBridge(resolution = Resolution.DAY)
    private Date date;

    @IndexedEmbedded(depth = 1)
    @OneToOne
    @JoinColumn(name = "destination")
    private Airport destination;

    @IndexedEmbedded(depth = 1)
    @OneToOne
    @JoinColumn(name = "departure")
    private Airport departure;

    @IndexedEmbedded
    @ManyToMany
    @JoinTable(
            name = "flightinfo",
            joinColumns = {@JoinColumn(name = "flight")},
            inverseJoinColumns = {@JoinColumn(name = "board")}
    )
    private List<Board> fromFlightToBoard;

    @ContainedIn
    @ManyToMany(mappedBy = "fromBoardToFlight")
    private List<Board> fromBoard;


    @Override
    public String toString() {
        return departure.getCity() + " - " + destination.getCity() + " #" + flightID;
    }
}

