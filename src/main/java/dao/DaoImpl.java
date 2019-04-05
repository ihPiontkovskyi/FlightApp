package dao;

import dao.Dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.Set;

@Data
public class DaoImpl<T> implements Dao<T> {
    ObservableList list = FXCollections.emptyObservableList();
    public ObservableList findAll(String tableName) {
        HibernateSessionFactoryUtil.doInHibernateSession(session -> {
            Query query = session.createQuery("from " + tableName);
            list = FXCollections.observableList(query.list());
        });

        return list;
    }

    public void delete(T t) {
        HibernateSessionFactoryUtil.doInHibernateSession(session -> {
            Transaction tx1 = session.beginTransaction();
            session.delete(t);
            tx1.commit();
        });
    }

    public void saveOrUpdate(Set<T> t) {
        HibernateSessionFactoryUtil.doInHibernateSession(session -> {
            Transaction tx1 = session.beginTransaction();
            t.forEach(session::saveOrUpdate);
            tx1.commit();
        });
    }
}