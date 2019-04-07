package models;

import lombok.Data;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Indexed
@Table(name = "ticket")
public class Ticket implements BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    @Field
    private double price;

    @Field
    private String classType;

    @Field
    @DateBridge(resolution= Resolution.DAY)
    private Date datePurchase;

    @ContainedIn
    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    @ContainedIn
    @ManyToOne
    @JoinColumn(name = "board")
    private Board board;
}
