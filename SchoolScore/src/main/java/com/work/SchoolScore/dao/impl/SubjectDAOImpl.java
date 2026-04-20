package com.work.SchoolScore.dao.impl;


import org.springframework.stereotype.Repository;

import com.work.SchoolScore.dao.SubjectDAO;
import com.work.SchoolScore.model.Subject;

// 使用 JPA 就不再使用 DAO 實作類別
//public class SubjectDAOImpl extends BaseDAOImpl<Subject> implements SubjectDAO  {
//
//	 public SubjectDAOImpl() {
//	        // 初始化原本的六筆資料
//	        save(create("國文"));
//	        save(create("英文"));
//	        save(create("數學"));
//	        save(create("自然"));
//	        save(create("社會"));
//	        save(create("其他"));
//	    }
//
//	    private Subject create(String name) {
//	        Subject s = new Subject();
//	        s.setName(name);
//	        return s;
//	    }
//	
//
//	@Override
//	protected void setId(Subject entity, Long id) {
//		entity.setId(id);
//	}
//
//
//	@Override
//	protected Long getId(Subject entity) {
//		return entity.getId();
//	}
//
//
//}
