package com.work.SchoolScore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.SchoolScore.dao.AssignmentDAO;
import com.work.SchoolScore.dao.CategoryDAO;
import com.work.SchoolScore.dao.HomeworkStatusDAO;
import com.work.SchoolScore.dao.SubjectDAO;
import com.work.SchoolScore.dto.assignment.AssignmentRequest;
import com.work.SchoolScore.model.Assignment;
import com.work.SchoolScore.model.Category;
import com.work.SchoolScore.model.HomeworkStatus;
import com.work.SchoolScore.model.Subject;
import com.work.SchoolScore.service.AssignmentService;

import jakarta.transaction.Transactional;

//這是 Service 的 實作
@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	private AssignmentDAO dao;

	@Autowired
	private SubjectDAO subjectDAO;

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private HomeworkStatusDAO homeworkStatusDAO;

	// 尋找全部
	@Override
	public List<Assignment> findAll() {
		return dao.findAll();
	}

	// 特定 ID 搜尋
	@Override
	public Optional<Assignment> findById(Long id) {
		return dao.findById(id);
	}


	// 新增
	/**
	 * 新增作業：核心邏輯改進 1. 驗證必填欄位 2. 儲存 Assignment 3. 自動生成 1~40 號 HomeworkStatus
	 */
	@Override
	@Transactional // 確保 40 筆狀態與作業同步成功或失敗
	public Assignment save(AssignmentRequest req) {
		
		// 顯式檢查 DTO 內的 ID 是否存在
	    if (req.subjectId() == null) throw new IllegalArgumentException("Subject ID 必須提供");
	    if (req.categoryId() == null) throw new IllegalArgumentException("Category ID 必須提供");

		Subject sub = subjectDAO.findById(req.subjectId()).orElseThrow();
		Category cate = categoryDAO.findById(req.categoryId()).orElseThrow();

		// 1️ 建立 Assignment
		Assignment assignment = new Assignment();
		assignment.setSubject(sub);
		assignment.setCategory(cate);
		assignment.setItem(req.item());
		assignment.setPageRange(req.pageRange());
		assignment.setAssignDate(req.assignDate());

		Assignment saved = dao.save(assignment);

		List<Integer> unsubmitted = Optional.
								    ofNullable(req.unsubmittedSeats()).
									orElse(new ArrayList<>());

		// 2️. 建立 40 筆 HomeworkStatus
		for (int seat = 1; seat <= 40; seat++) {

			HomeworkStatus hs = new HomeworkStatus();
			hs.setAssignment(saved);
			hs.setSeatNo(seat);

			// ⭐ 預設已交
			hs.setSubmitted(true);

			// ⭐ 如果在未交清單 → 設為 false
			if (unsubmitted.contains(seat)) {
				hs.setSubmitted(false);
			}

			homeworkStatusDAO.save(hs);
		}

		return saved;
	}

	// 更新
	@Override
	@Transactional
	public Optional<Assignment> update(Long id,AssignmentRequest req) {
		
		// 1.驗證 作業是否存在
		Assignment result = dao.findById(id).orElseThrow(
				()->new RuntimeException("Assignment 不存在")
				);
		
		// 2.更新作業本體
		Subject sub = subjectDAO.findById(req.subjectId()).orElseThrow();
		Category cate = categoryDAO.findById(req.categoryId()).orElseThrow();

		result.setSubject(sub);
		result.setCategory(cate);
		result.setItem(req.item());
		result.setPageRange(req.pageRange());
		result.setAssignDate(req.assignDate());
		
		Assignment saved = dao.save(result); 
		
		
		// 3.刪除 舊未交名單
		homeworkStatusDAO.deleteByAssignmentId(id);
		
		// 4.重新建立 未交名單
		List<Integer> unsubmitted = Optional.
			    ofNullable(req.unsubmittedSeats()).
				orElse(new ArrayList<>());
		
		List<HomeworkStatus> statusList = new ArrayList<>();

	    for (int seat = 1; seat <= 40; seat++) {
	        HomeworkStatus hs = new HomeworkStatus();
	        hs.setAssignment(saved);
	        hs.setSeatNo(seat);
	        hs.setSubmitted(!unsubmitted.contains(seat)); // 簡化邏輯
	        statusList.add(hs);
	    }
	    
	    homeworkStatusDAO.saveAll(statusList); // 改用批次儲存提升效能
		
		
		return Optional.ofNullable(saved);
		
	}

	// 刪除
	@Override
	@Transactional
	public boolean delete(Long id) {
		return dao.findById(id).map(o -> {
			dao.delete(o);
			return true;
		}).orElse(false);
	}

}
