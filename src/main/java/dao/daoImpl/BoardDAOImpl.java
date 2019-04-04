package dao.daoImpl;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Board;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class BoardDAOImpl implements DAO<Board> {
    public BoardDAOImpl() {
    }

    public ObservableList<Board> FindAll() {
        try {
            List<Board> boards = (List<Board>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from Board").list();
            ObservableList<Board> list = FXCollections.observableArrayList(boards);
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public void DeleteItem(Board board) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(board);
        tx1.commit();
        session.close();
    }
    public void UpdateItems(ObservableList<Board> boards)
    {}

}
