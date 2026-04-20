package com.work.SchoolScore.dao.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.work.SchoolScore.dao.AssignmentDAO;
import com.work.SchoolScore.dao.CategoryDAO;
import com.work.SchoolScore.dao.SubjectDAO;
import com.work.SchoolScore.model.Assignment;
import com.work.SchoolScore.model.Category;
import com.work.SchoolScore.model.Subject;


//public class AssignmentDAOImpl extends BaseDAOImpl<Assignment> implements AssignmentDAO {
//
//	private final SubjectDAO subjectDAO;
//    private final CategoryDAO categoryDAO;
//	
// // 使用構造函數注入確保初始化順序
//    @Autowired
//    public AssignmentDAOImpl(SubjectDAO subjectDAO, CategoryDAO categoryDAO) {
//        this.subjectDAO = subjectDAO;
//        this.categoryDAO = categoryDAO;
//        initMockData();
//    }
//    
//    private void initMockData() {
//        // 取得國文(1L)與習作(1L)物件
//        Optional<Subject> s1 = subjectDAO.findById(1L);
//        Optional<Category> c1 = categoryDAO.findById(1L);
//
//        if (s1.isPresent() && c1.isPresent()) {
//            Assignment a1 = new Assignment();
//            a1.setSubject(s1.get());    // 注入完整物件 
//            a1.setCategory(c1.get());   // 注入完整物件 
//            a1.setItem("第二次習作");
//            a1.setPageRange("3~10");
//            a1.setAssignDate(LocalDate.now());
//            save(a1);
//        }
//        
//    }
//	
//	
//	@Override
//	protected void setId(Assignment entity, Long id) {
//		entity.setId(id);
//	}
//
//	@Override
//	protected Long getId(Assignment entity) {
//		return entity.getId();
//	}
//
//	
//
//}
