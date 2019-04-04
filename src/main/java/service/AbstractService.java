package service;

import javafx.collections.ObservableList;

public interface AbstractService<T> {
    ObservableList<T> FindAll();
    void Delete(T t);
}
