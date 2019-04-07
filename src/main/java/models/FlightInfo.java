package models;

import lombok.Data;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Indexed
@Table (name = "flightinfo")
public class FlightInfo implements BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flight_infoID;

    @ContainedIn
    @ManyToOne
    @JoinColumn(name = "flight")
    private Flight flight;

    @ContainedIn
    @ManyToOne
    @JoinColumn(name = "board")
    private Board board;


}
