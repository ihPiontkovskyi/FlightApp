package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import models.Airport;
import models.Flight;
import models.Ticket;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.search.query.dsl.QueryBuilder;
import utils.HibernateSessionFactoryUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

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

    public void saveOrUpdate(ObservableList t) {
        HibernateSessionFactoryUtil.doInHibernateSession(session -> {
            Transaction tx1 = session.beginTransaction();
            t.forEach(session::saveOrUpdate);
            tx1.commit();
        });
    }
    public ObservableList search(Map fieldValue) {
        final Set<T> result = new HashSet<T>();

        HibernateSessionFactoryUtil.doInHibernateSession(session -> {
            Transaction transaction = null;
            try {
                transaction = session.getTransaction();
            transaction.begin();

            FullTextSession fullTextSession = Search.getFullTextSession(session);
            fullTextSession.createIndexer().startAndWait();

            QueryBuilder qb = fullTextSession.getSearchFactory()
                    .buildQueryBuilder().forEntity((Class)this.getClass().getGenericSuperclass()).get();
            AtomicReference<Integer> step = new AtomicReference<>(0);
            fieldValue.forEach((k,v)-> {
                        org.apache.lucene.search.Query luceneQuery = qb.keyword().onField(k.toString()).matching(v).createQuery();
                org.apache.lucene.search.Query luceneQuery1 = qb.keyword().onField(k.toString()).matching(v).createQuery();
                        Query jpaQuery = null;
                        try {
                           jpaQuery = fullTextSession.createFullTextQuery(luceneQuery, (Class)this.getClass().getGenericSuperclass());

                        } catch (Exception ex){
                            ex.printStackTrace();
                    }
                    List res = jpaQuery.getResultList();
                    if(step.get() == 0)
                    {
                        result.addAll(res);
                    }
                    else {
                        result.retainAll(res);
                    }
                    step.getAndSet(step.get() + 1);
                });
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    });
        return FXCollections.observableArrayList(result);
    }

}