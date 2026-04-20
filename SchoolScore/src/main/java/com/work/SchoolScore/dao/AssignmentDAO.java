package com.work.SchoolScore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.work.SchoolScore.model.Assignment;

@Repository
public interface AssignmentDAO extends JpaRepository<Assignment, Long>, JpaSpecificationExecutor<Assignment>{
	
	// 根據科目 ID 查詢該科所有作業 
    List<Assignment> findBySubjectId(Long subjectId);
    
    // 根據類別 ID 查詢作業
    List<Assignment> findByCategoryId(Long categoryId);
	
}