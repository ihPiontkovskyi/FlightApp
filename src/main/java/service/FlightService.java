package service;

import dao.DAO;
import dao.daoImpl.FlightDAOImpl;
import javafx.collections.ObservableList;
import models.Flight;

public class FlightService implements AbstractService<Flight> {
    private DAO<Flight> flightDAO = new FlightDAOImpl();

    public FlightService() {
    }
    public ObservableList<Flight> FindAll()
    {
        return flightDAO.FindAll();
    }
    public void Delete(Flight flight){ flightDAO.DeleteItem(flight);}
    public void Update(ObservableList<Flight> flights ) { }
}
