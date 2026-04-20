package com.work.SchoolScore.dao.impl;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.work.SchoolScore.dao.BaseDAO;

// 使用 JPA 前 的過渡
public abstract class BaseDAOImpl<T> implements BaseDAO<T, Long>{

	// 使用 ConcurrentHashMap 避免多執行緒操作衝突
	protected Map<Long, T> dataStore = new ConcurrentHashMap<>();
	
	//模擬「資料庫的自動遞增主鍵（AUTO_INCREMENT）」
	protected AtomicLong idGenerator = new AtomicLong(1);

	protected abstract void setId(T entity, Long id);
	protected abstract Long getId(T entity);

	public List<T> findAll() {
		return new ArrayList<>(dataStore.values());
	}

	public Optional<T> findById(Long id) {
		return Optional.ofNullable(dataStore.get(id));
	}

	public T save(T entity) {
		
		if (getId(entity) == null) {
            Long id = idGenerator.getAndIncrement();
            setId(entity, id);
        }
		dataStore.put(getId(entity), entity);
        return entity;
	}

	public void delete(Long id) {
		dataStore.remove(id);
	}
}
