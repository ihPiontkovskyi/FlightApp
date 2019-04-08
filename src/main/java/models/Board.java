package models;

import lombok.Data;
import models.bridges.StringToNumberBridge;
import org.hibernate.annotations.Target;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Indexed
@Table(name = "board")
public class Board implements BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardID;

    @Field(analyze = Analyze.NO)
    @Temporal(TemporalType.DATE)
    @DateBridge(resolution = Resolution.DAY)
    private Date lastRepair;

    @Field
    private String jetType;

    @Field
    @FieldBridge(impl = StringToNumberBridge.class)
    private int freeSeat;

    @ContainedIn
    @ManyToMany(mappedBy = "fromFlightToBoard")
    private List<Flight> fromFlightToBoard;

    @IndexedEmbedded(depth = 2)
    @ManyToMany
    @JoinTable(
            name = "flightinfo",
            joinColumns = {@JoinColumn(name = "board")},
            inverseJoinColumns = {@JoinColumn(name = "flight")}
    )
    private List<Flight> fromBoardToFlight;

    @IndexedEmbedded(depth = 1)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;


    @IndexedEmbedded(depth = 1)
    @ManyToMany
    @JoinTable(
            name = "ticket",
            joinColumns = {@JoinColumn(name = "board")},
            inverseJoinColumns = {@JoinColumn(name = "client")}
    )
    private List<Client> fromBoardToClient;

    @IndexedEmbedded(depth = 1)
    @ManyToMany(mappedBy = "fromClientToBoard")
    private List<Client> fromClientToBoard;

    @Override
    public String toString() {
        return jetType + " #" + boardID;
    }

}
