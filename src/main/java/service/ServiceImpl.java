package service;

import dao.Dao;
import dao.DaoImpl;
import javafx.collections.ObservableList;

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

    public void update(ObservableList<T> t) {
        dao.update(t);
    }
}

