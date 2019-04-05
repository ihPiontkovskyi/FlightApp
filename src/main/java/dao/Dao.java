package dao;

import javafx.collections.ObservableList;

public interface Dao<T> {

	ObservableList<T> findAll(String tableName);

	void delete(T t);

	void update(ObservableList<T> t);
}

