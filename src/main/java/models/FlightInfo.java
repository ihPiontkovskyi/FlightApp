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
    @ManyToOne(fetch = FetchType.LAZY)
    private Flight flight;
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;


}
