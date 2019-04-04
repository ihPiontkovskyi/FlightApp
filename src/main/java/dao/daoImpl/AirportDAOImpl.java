package dao.daoImpl;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Airport;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class AirportDAOImpl implements DAO<Airport> {
    public AirportDAOImpl() {
    }

    public ObservableList<Airport> FindAll() {
        try {
            List<Airport> airports = (List<Airport>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from Airport").list();
            ObservableList<Airport> list = FXCollections.observableArrayList(airports);
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void DeleteItem(Airport airport) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.delete(airport);
            tx1.commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void UpdateItems(ObservableList<Airport> airports)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        airports.forEach(e -> session.update(e));
        tx1.commit();
        session.close();
    }
}