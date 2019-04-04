package service;

import dao.DAO;
import dao.daoImpl.FlightInfoDAOImpl;
import javafx.collections.ObservableList;
import models.FlightInformation;

public class FlightInformationService implements AbstractService<FlightInformation> {
    private DAO<FlightInformation> flightInfoDAO = new FlightInfoDAOImpl();

    public FlightInformationService() {
    }

    public ObservableList<FlightInformation> FindAll() {
        return flightInfoDAO.FindAll();
    }

    public void Delete(FlightInformation flightInformation) {
        flightInfoDAO.DeleteItem(flightInformation);
    }
    public void Update(ObservableList<FlightInformation> flightInfos) { }
}
