package com.work.SchoolScore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.work.SchoolScore.model.HomeworkStatus;

@Repository
public interface HomeworkStatusDAO extends JpaRepository<HomeworkStatus, Long>, JpaSpecificationExecutor<HomeworkStatus> {
		
	// 1. 根據座號查詢未繳作業
    // SQL: SELECT * FROM homework_status WHERE seat_no = ? AND submitted = false
    List<HomeworkStatus> findBySeatNoAndSubmittedFalse(int seatNo);
    
    // 使用 JPQL 簡化：只需要傳入一個 seatNo
    @Query("SELECT h FROM HomeworkStatus h " +
            "JOIN FETCH h.assignment a " +
            "JOIN FETCH a.subject s " +
            "JOIN FETCH a.category c " +
            "WHERE h.seatNo = :seatNo " +
            "AND (h.submitted = false ) " +
            "ORDER BY s.orderNo ASC, c.orderNo ASC")
    List<HomeworkStatus> findUnfinishedBySeatNo(@Param("seatNo") int seatNo);

    // 2. 根據作業 ID 查詢所有學生的繳交狀況 
    List<HomeworkStatus> findByAssignmentId(Long assignmentId);

    // 3. 根據科目查詢該科所有未繳記錄
    // 透過關聯路徑 Assignment -> Subject 進行過濾
    List<HomeworkStatus> findByAssignment_Subject_IdAndSubmittedFalse(Long subjectId);
    
    // 4. 根據作業 ID 刪除 HomeworkStatus
    // DELETE FROM HomeworkStatus hs WHERE hs.assignment.id = :assignmentId
    void deleteByAssignmentId(Long assignmentId);

}
