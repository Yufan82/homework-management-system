package com.work.SchoolScore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.work.SchoolScore.model.Category;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category>{

	// 額外定義：依科目 ID 查詢類別
    List<Category> findBySubjectId(Long subjectId);
}
