package service;

import dao.DAO;
import dao.daoImpl.AirportDAOImpl;
import javafx.collections.ObservableList;
import models.Airport;

public class AirportService implements AbstractService<Airport> {
    private DAO<Airport> airportDAO = new AirportDAOImpl();

    public AirportService() {
    }

    public ObservableList<Airport> FindAll() {
        return airportDAO.FindAll();
    }
    public void Delete(Airport airport){ airportDAO.DeleteItem(airport);}
}
