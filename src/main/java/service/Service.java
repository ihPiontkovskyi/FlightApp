package service;

import javafx.collections.ObservableList;

import java.util.Map;
import java.util.Set;

public interface Service<T> {

	ObservableList<T> findAll(String tableName);

	void delete(T t);

	void saveOrUpdate(ObservableList set);

	ObservableList search(Map map);
}
