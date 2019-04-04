package dao.daoImpl;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.FlightInformation;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class FlightInfoDAOImpl implements DAO<FlightInformation> {
    public FlightInfoDAOImpl() {
    }
    public ObservableList<FlightInformation> FindAll() {
        try {
            List<FlightInformation> flightInformation = (List<FlightInformation>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from FlightInformation").list();
            ObservableList<FlightInformation> list = FXCollections.observableArrayList(flightInformation);
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public void DeleteItem(FlightInformation flightInformation) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(flightInformation);
        tx1.commit();
        session.close();
    }
}
