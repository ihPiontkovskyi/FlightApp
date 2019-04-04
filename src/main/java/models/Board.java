package models;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
public class Board implements BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardID;
    private Date last_repair;
    private String jet_type;
    private int available_seat;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlightInformation> flightInfos;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    public Board() {
    }

    @Override
    public String toString() {
        return jet_type + " #" + boardID;
    }

    public int getBoardID() {
        return boardID;
    }

    public Date getLast_repair() {
        return last_repair;
    }

    public void setLast_repair(Date last_repair) {
        this.last_repair = last_repair;
    }

    public String getJet_type() {
        return jet_type;
    }

    public void setJet_type(String jet_type) {
        this.jet_type = jet_type;
    }

    public int getAvailable_seat() {
        return available_seat;
    }

    public void setAvailable_seat(int available_seat) {
        this.available_seat = available_seat;
    }

    public void addFlightInformation(FlightInformation flightInfo) {
        flightInfo.setBoard(this);
        flightInfos.add(flightInfo);
    }

    public void removeFlightInformation(FlightInformation flightInfo) {
        flightInfos.remove(flightInfo);
    }

    public List<FlightInformation> getFlightInfos() {
        return flightInfos;
    }

    public void setFlightInfos(List<FlightInformation> flightInfos) {
        this.flightInfos = flightInfos;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void addTicket(Ticket ticket) {
        ticket.setBoard(this);
        tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }
}
