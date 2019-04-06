package service;

import dao.Dao;
import dao.DaoImpl;
import javafx.collections.ObservableList;

import java.util.Map;
import java.util.Set;

public class ServiceImpl<T> implements Service<T> {

    private Dao<T> dao;

    public ServiceImpl() {
        dao = new DaoImpl<T>();
    }

    public ObservableList<T> findAll(String tableName) {
        return dao.findAll(tableName);
    }

    public void delete(T t) {
        dao.delete(t);
    }

    public void saveOrUpdate(Set<T> t) {
        dao.saveOrUpdate(t);
    }

    public ObservableList search(Map map) {
       return dao.search(map);
    }
}

