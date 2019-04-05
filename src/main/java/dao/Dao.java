package dao;

import javafx.collections.ObservableList;

import java.util.Set;

public interface Dao<T> {

	ObservableList<T> findAll(String tableName);

	void delete(T t);

	void saveOrUpdate(Set<T> t);
}

