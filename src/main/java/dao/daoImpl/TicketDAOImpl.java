package dao.daoImpl;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Ticket;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class TicketDAOImpl implements DAO<Ticket> {
    public TicketDAOImpl() {
    }

    public ObservableList<Ticket> FindAll() {
        try {
            List<Ticket> tickets = (List<Ticket>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from Ticket").list();
            ObservableList<Ticket> list = FXCollections.observableArrayList(tickets);
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public void DeleteItem(Ticket ticket) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(ticket);
        tx1.commit();
        session.close();
    }
    public void UpdateItems(ObservableList<Ticket> tickets)
    {}
}
