package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import utils.HibernateSessionFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    public ObservableList search(Map fieldValue) {
        final Set<T> result = new HashSet<T>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager em = entityManagerFactory.createEntityManager();
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        em.getTransaction().begin();

        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(this.getClass().getGenericSuperclass().getClass()).get();

        fieldValue.forEach((k,v)->{
            org.apache.lucene.search.Query luceneQuery = qb.keyword().onField(k.toString()).matching(v).createQuery();
            javax.persistence.Query jpaQuery =
                    fullTextEntityManager.createFullTextQuery(luceneQuery, this.getClass().getGenericSuperclass().getClass());

            List res = jpaQuery.getResultList();
            Set tmp = new HashSet<T>();
            tmp.addAll(res);
            tmp.retainAll(result);
            result.clear();
            result.addAll(tmp);
        });

        return FXCollections.observableArrayList(result);
    }

}