package models;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Table (name = "flight")
public class Flight implements BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightID;
    private Time duration;
    private Date date;
    @OneToOne
    @JoinColumn(name="destinationID")
    private Airport destination;
    @OneToOne
    @JoinColumn(name="departureID")
    private Airport departure;
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlightInformation> boardInfos;

    public Flight() {
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFlightID() {
        return flightID;
    }
    public List<FlightInformation> getBoardInfos() {
        return boardInfos;
    }

    public void setBoardInfos(List<FlightInformation> boardInfos) {
        this.boardInfos = boardInfos;
    }

    public void addBoardInformation(FlightInformation boardInfo) {
        boardInfo.setFlight(this);
        boardInfos.add(boardInfo);
    }

    public void removeBoardInformation(FlightInformation boardInfo) {
        boardInfos.remove(boardInfo);
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public Airport getDeparture() {
        return departure;
    }

    public void setDeparture(Airport departure) {
        this.departure = departure;
    }

    @Override
    public String toString() {
        return departure.getCity() +" - "+destination.getCity()  + " #" + flightID;
    }

}

