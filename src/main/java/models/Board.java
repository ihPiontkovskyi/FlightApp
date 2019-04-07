package models;

import lombok.Data;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Indexed
@Table(name = "board")
public class Board implements BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardID;

    @Field
    @DateBridge(resolution = Resolution.DAY)
    private Date lastRepair;

    @Field
    private String jetType;

    @Field
    private int freeSeat;

    @IndexedEmbedded
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    @ContainedIn
    @ManyToMany(mappedBy = "boards")
    private List<Flight> flights;

    @IndexedEmbedded
    @ManyToMany
    @JoinTable(
            name = "ticket",
            joinColumns = {@JoinColumn(name = "board")},
            inverseJoinColumns = {@JoinColumn(name = "client")}
    )
    private List<Client> clients;


    @Override
    public String toString() {
        return jetType + " #" + boardID;
    }

}
