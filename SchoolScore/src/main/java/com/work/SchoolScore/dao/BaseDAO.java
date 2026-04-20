package com.work.SchoolScore.dao;

import java.util.List;
import java.util.Optional;

// 建立共同規範的 泛型 DAO
public interface BaseDAO <T, ID>{
	
	List<T> findAll();

	Optional<T> findById(ID id);

    T save(T entity);

    void delete(ID id);

}
