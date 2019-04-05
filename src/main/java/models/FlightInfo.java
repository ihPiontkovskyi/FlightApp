package models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table (name = "flightinfo")
public class FlightInfo implements BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flight_infoID;
    @ManyToOne
    @JoinColumn(name = "flight")
    private Flight flight;
    @ManyToOne
    @JoinColumn(name = "board")
    private Board board;


}
