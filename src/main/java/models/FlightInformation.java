package models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table (name = "flight_info")
public class FlightInformation implements BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flight_infoID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flightID")
    private Flight flight;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardID")
    private Board board;

    public int getFlight_infoId() {
        return flight_infoID;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}
