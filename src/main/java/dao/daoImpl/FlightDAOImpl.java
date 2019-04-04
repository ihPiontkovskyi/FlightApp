package dao.daoImpl;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Flight;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class FlightDAOImpl implements DAO<Flight> {
    public FlightDAOImpl() {
    }

    public ObservableList<Flight> FindAll() {
        try {
            List<Flight> flights = (List<Flight>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from Flight").list();
            ObservableList<Flight> list = FXCollections.observableArrayList(flights);
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public void DeleteItem(Flight flight) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(flight);
        tx1.commit();
        session.close();
    }
}
