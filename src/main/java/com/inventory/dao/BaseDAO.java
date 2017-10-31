package com.inventory.dao;

import java.util.List;
import java.util.Map;

public interface BaseDAO<T,ID> {
	void save(T t);
	void delete(ID[] ids);
	void update(T t);
	T findById(ID id);
	List<T> findAll(Map<String,Object> map);
	Long countAll(Map<String,Object> map);
}
