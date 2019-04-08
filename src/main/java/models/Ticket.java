package models;

import lombok.Data;
import models.bridges.StringToNumberBridge;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Indexed
@Table(name = "ticket")
public class Ticket implements BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    @Field
    @FieldBridge(impl = StringToNumberBridge.class)
    private double price;

    @Field
    private String classType;

    @Field
    @Temporal(TemporalType.DATE)
    @DateBridge(resolution = Resolution.DAY)
    private Date datePurchase;

    @IndexedEmbedded(depth = 3)
    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    @IndexedEmbedded(depth = 3)
    @ManyToOne
    @JoinColumn(name = "board")
    private Board board;
}
