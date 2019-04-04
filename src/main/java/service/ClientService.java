package service;

import dao.DAO;
import dao.daoImpl.ClientDAOImpl;
import javafx.collections.ObservableList;
import models.Client;

public class ClientService implements AbstractService<Client> {
    private DAO<Client> clientDAO = new ClientDAOImpl();

    public ClientService() {
    }

    public ObservableList<Client> FindAll() {
        return clientDAO.FindAll();
    }
    public void Delete(Client client){ clientDAO.DeleteItem(client);}
    public void Update(ObservableList<Client> clients) { }
}
