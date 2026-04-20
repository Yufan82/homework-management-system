package com.work.SchoolScore.dao.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.work.SchoolScore.dao.HomeworkStatusDAO;
import com.work.SchoolScore.model.*;


//public class HomeworkStatusDAOImpl extends BaseDAOImpl<HomeworkStatus> implements HomeworkStatusDAO {
//
//	public HomeworkStatusDAOImpl(){
//		
//		// 建立兩個假 Assignment
//        Assignment assignment1 = new Assignment();
//        assignment1.setId(1L);
//
//        Assignment assignment2 = new Assignment();
//        assignment2.setId(2L);
//		
//		// 針對 assignmentId = 1 產生 40 筆
//        for (int seat = 1; seat <= 40; seat++) {
//
//            HomeworkStatus status = new HomeworkStatus();
//            status.setId(idGenerator.getAndIncrement());
//            status.setAssignment(assignment1);
//            status.setSeatNo(seat);
//
//            // 模擬部分未交
//            boolean submitted = seat % 3 != 0;
//            status.setSubmitted(submitted);
//
//            if (submitted) {
//                status.setSubmitTime(LocalDateTime.now().minusDays(1));
//            }
//
//            status.setCorrected(false);
//
//            save(status);
//        }
//        
//     // 針對 assignmentId = 2 產生 40 筆
//        for (int seat = 1; seat <= 40; seat++) {
//
//            HomeworkStatus status2 = new HomeworkStatus();
//            status2.setId(idGenerator.getAndIncrement());
//            status2.setAssignment(assignment2);
//            status2.setSeatNo(seat);
//
//            // 模擬一半已交
//            boolean submitted = seat % 2 == 0;
//            status2.setSubmitted(submitted);
//
//            if (submitted) {
//                status2.setSubmitTime(LocalDateTime.now());
//                status2.setCorrected(seat % 4 == 0);
//            }
//
//            save(status2);
//        }
//	}
//	
//	// 提供批次儲存方法
//	public void saveAll(List<HomeworkStatus> list) {
//		list.forEach(this::save);
//	}
//
//	// 提供依作業 ID 查詢的方法
//	public List<HomeworkStatus> findByAssignmentId(Long assignmentId) {
//		return dataStore.values()
//				.stream()
//				.filter(s -> s.getAssignmentId().equals(assignmentId))
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	protected void setId(HomeworkStatus entity, Long id) {
//		entity.setId(id);
//	}
//
//	@Override
//	protected Long getId(HomeworkStatus entity) {
//		return entity.getId();
//	}
//
//}
