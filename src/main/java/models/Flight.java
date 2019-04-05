package models;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
@Entity
@Table (name = "flight")
public class Flight implements BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightID;
    private Time duration;
    private Date date;
    @OneToOne
    @JoinColumn(name = "destination")
    private Airport destination;
    @OneToOne
    @JoinColumn(name = "departure")
    private Airport departure;
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlightInfo> boardInfos;

    public void addBoardInformation(FlightInfo boardInfo) {
        boardInfo.setFlight(this);
        boardInfos.add(boardInfo);
    }
    public void removeBoardInformation(FlightInfo boardInfo) {
        boardInfos.remove(boardInfo);
    }
    @Override
    public String toString() {
        return departure.getCity() +" - "+destination.getCity()  + " #" + flightID;
    }

}

