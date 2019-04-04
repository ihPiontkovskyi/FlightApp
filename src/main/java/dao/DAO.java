package dao;

import javafx.collections.ObservableList;

public interface DAO<T> {
    ObservableList<T> FindAll();
    void DeleteItem(T board);
    void UpdateItems(ObservableList<T> listT);
}

