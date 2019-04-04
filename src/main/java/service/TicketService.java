package service;

import dao.DAO;
import dao.daoImpl.TicketDAOImpl;
import javafx.collections.ObservableList;
import models.Ticket;

public class TicketService implements AbstractService<Ticket> {
    private DAO<Ticket> ticketDAO = new TicketDAOImpl();

    public TicketService() {
    }

    public ObservableList<Ticket> FindAll() {
        return ticketDAO.FindAll();
    }

    public void Delete(Ticket ticket) {
        ticketDAO.DeleteItem(ticket);
    }
    public void Update(ObservableList<Ticket> tickets ) { }
}
