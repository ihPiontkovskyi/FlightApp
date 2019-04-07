package service;

import dao.Dao;
import dao.DaoImpl;
import javafx.collections.ObservableList;

import java.util.Map;

public class ServiceImpl<T> implements Service<T> {

    private Dao<T> dao;

    public ServiceImpl() {
        dao = new DaoImpl<>();
    }

    public ObservableList<T> findAll(String tableName) {
        return dao.findAll(tableName);
    }

    public void delete(T t) {
        dao.delete(t);
    }

    public void saveOrUpdate(ObservableList t) {
        dao.saveOrUpdate(t);
    }

    public ObservableList search(Map map) {
        return dao.search(map);
    }
}

