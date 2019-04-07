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
@Table (name = "flight")
public class Flight implements BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightID;

    @Field
    private Time duration;

    @Field
    @DateBridge(resolution= Resolution.DAY)
    private Date date;

    @IndexedEmbedded
    @OneToOne
    @JoinColumn(name = "destination")
    private Airport destination;

    @IndexedEmbedded
    @OneToOne
    @JoinColumn(name = "departure")
    private Airport departure;

    @IndexedEmbedded
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlightInfo> boardInfos;

    @Override
    public String toString() {
        return departure.getCity() +" - "+destination.getCity()  + " #" + flightID;
    }
}

