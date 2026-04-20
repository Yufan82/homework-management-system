package com.work.SchoolScore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.work.SchoolScore.model.Subject;

/*	
  	使用泛型
	public interface SubjectDAO extends BaseDAO<Subject, Long> {
	}
*/

/*
public interface SubjectDAO extends 
	CrudRepository<Subject,Long>,        	// 針對 CRUD
	JpaSpecificationExecutor<Subject>,   	// 針對 JPA 的排序
	PagingAndSortingRepository<Subject,Long>	// 動態查詢條件
{
}
*/

/**
 * SubjectDAO 介面
 * 繼承 JpaRepository 以獲得基本 CRUD、分頁與排序功能
 * 繼承 JpaSpecificationExecutor 以支援動態查詢 (Specification)
 */
@Repository
public interface SubjectDAO extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {
    // JpaRepository 已包含 CrudRepository 與 PagingAndSortingRepository 的功能
	// 不再使用 DAO 實作類別（SubjectDAOImpl）
}
