package com.work.SchoolScore.service;

import java.util.List;

import com.work.SchoolScore.dto.homework.HomeworkStatusRequest;
import com.work.SchoolScore.model.HomeworkStatus;

public interface HomeworkStatusService {
	
	// 根據作業 ID 撈出全班 40 筆狀態
	List<HomeworkStatus> findByAssignmentId(Long assignmentId);
	
	// 查詢未交名單
	List<HomeworkStatus> findUnsubmitted(Long assignmentId);
	
	// 修改單筆狀態
	HomeworkStatus update(Long id, HomeworkStatusRequest req);
	
	// 依科目查詢未完成作業
	List<HomeworkStatus> findUnfinishedBySubject(Long subjectId);
	
	// 依座號查詢未完成作業
	List<HomeworkStatus> findUnfinishedBySeatNo(int seatNo);

	// 依作業 ID查詢 刪除未完成作業
	void deleteByAssignmentId(Long assignmentId);


}
