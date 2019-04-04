package dao.daoImpl;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class ClientDAOImpl implements DAO<Client> {
    public ClientDAOImpl() {
    }

    public ObservableList<Client> FindAll() {
        try {
            List<Client> clients = (List<Client>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from Client").list();
            ObservableList<Client> list = FXCollections.observableArrayList(clients);
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public void DeleteItem(Client client) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(client);
        tx1.commit();
        session.close();
    }
}
