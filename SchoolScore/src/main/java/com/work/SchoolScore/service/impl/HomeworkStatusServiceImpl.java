package com.work.SchoolScore.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.SchoolScore.dao.AssignmentDAO;
import com.work.SchoolScore.dao.HomeworkStatusDAO;
import com.work.SchoolScore.dto.homework.HomeworkStatusRequest;
import com.work.SchoolScore.model.Assignment;
import com.work.SchoolScore.model.HomeworkStatus;
import com.work.SchoolScore.service.HomeworkStatusService;

import jakarta.transaction.Transactional;

//這是 Service 的 實作
@Service
public class HomeworkStatusServiceImpl implements HomeworkStatusService {

	@Autowired
	private HomeworkStatusDAO dao;

	@Autowired
	private AssignmentDAO assignmentDao;

	// 根據作業 ID 撈出全班 40 筆狀態
	@Override
	public List<HomeworkStatus> findByAssignmentId(Long assignmentId) {
		return dao.findByAssignmentId(assignmentId);
	}

	// 查詢未交名單
	@Override
	public List<HomeworkStatus> findUnsubmitted(Long assignmentId) {
		return dao.findByAssignmentId(assignmentId)
				.stream()
				.filter(s -> !s.isSubmitted())
				.collect(Collectors.toList());
	}

	// 刪除 特定作業
	@Override
	public void deleteByAssignmentId(Long assignmentId) {

		List<HomeworkStatus> list = dao.findByAssignmentId(assignmentId);

		list.forEach(dao::delete);

//		 dao.deleteByAssignmentId(assignmentId);

	}

	// 更新
	@Override
	@Transactional
	public HomeworkStatus update(Long id, HomeworkStatusRequest req) {
		// 先取出資料，若不存在則拋出異常
		HomeworkStatus existing = dao.findById(id).orElseThrow(() -> new RuntimeException("找不到該狀態紀錄 ID: " + id));

		// 處理
		existing.setSubmitted(req.submitted());
		existing.setCorrected(req.corrected());

		
		//  可加：時間邏輯（這才是 Service 該做的！）
	    if (req.submitted()) {
	        existing.setSubmitTime(java.time.LocalDateTime.now());
	    }

	    if (req.corrected()) {
	        existing.setCorrectTime(java.time.LocalDateTime.now());
	    }
		
		// 儲存並回傳
		return dao.save(existing);
	}

	// 依科目查詢未完成作業
	@Override
	public List<HomeworkStatus> findUnfinishedBySubject(Long subjectId) {
		
		// 使用子方法
		List<Long> assignmentIds = getAssignmentBySubjectId(subjectId);
		
		return filterUnfinishedStatus(assignmentIds);
		
		
//		// 1. 找出該科目下所有的 Assignment 物件
//		List<Assignment> assignments = assignmentDao.findAll().stream()
//				.filter(a -> a.getSubject() != null && a.getSubject().getId().equals(subjectId)).toList();
//
//		// 2. 獲取這些作業的 ID 清單
//		List<Long> targetIds = assignments.stream().map(Assignment::getId).toList();
//
//		// 3. 篩選狀態：作業 ID 匹配且（未繳 OR 未訂正）
//		return dao.findAll().stream()
//				.filter(s -> s.getAssignment() != null && targetIds.contains(s.getAssignment().getId()))
//				.filter(s -> !s.isSubmitted() || !s.isCorrected()).collect(Collectors.toList());
	
	}
	
	// 分成 子方法
	protected List<Long> getAssignmentBySubjectId(Long subjectId){
		return assignmentDao.findAll()
				.stream()
				.filter(a -> a.getSubject() != null && a.getSubject().getId().equals(subjectId))
				.map(Assignment::getId)
				.toList();
	}
	
	protected List<HomeworkStatus> filterUnfinishedStatus(List<Long>assignmentIds){
		return dao.findAll().stream()
				.filter(s -> s.getAssignment() != null && assignmentIds.contains(s.getAssignment().getId()))
				.filter(s -> !s.isSubmitted() || !s.isCorrected()).collect(Collectors.toList());
	}

	// 依座號查詢未完成作業
	@Override
	public List<HomeworkStatus> findUnfinishedBySeatNo(int seatNo) {

		return dao.findUnfinishedBySeatNo(seatNo);
	}


}
