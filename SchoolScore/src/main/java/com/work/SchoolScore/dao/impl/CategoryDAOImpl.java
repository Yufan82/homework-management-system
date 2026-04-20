package com.work.SchoolScore.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.work.SchoolScore.dao.CategoryDAO;
import com.work.SchoolScore.model.Category;
import com.work.SchoolScore.model.Subject;


//public class CategoryDAOImpl extends BaseDAOImpl<Category> implements CategoryDAO {
//
//	public CategoryDAOImpl() {
//		
//		// 初始化 5 筆測試用假資料
//        // 假設 Subject ID: 1-國文, 2-英文, 3-數學, 4-自然, 5-社會, 6-其他
//		save(add("習作",1L));
//		save(add("練習題",1L));
//		save(add("單字小考",2L));
//		save(add("測驗",3L));
//		save(add("通知單",6L));
//		
//	}
//
//	private static Category add(String name,Long subjectId) {
//		Category c = new Category();
//		c.setName(name);
//		
//		// 建立一個僅含 ID 的 Subject 物件作為關聯錨點
//        Subject s = new Subject();
//        s.setId(subjectId);
//        c.setSubject(s);
//		
//		return c;
//		
//	}
//	
//	// 實作 CategoryDAO 介面要求的篩選功能
//	@SuppressWarnings("unlikely-arg-type")
//	@Override
//	public List<Category> findBySubjectId(Long subjectId) {
//		return dataStore.values().stream()
//                .filter(c -> c.getSubject().equals(subjectId))
//                .collect(Collectors.toList());
//	}
//
//	@Override
//	protected void setId(Category entity, Long id) {
//		 entity.setId(id);
//	}
//
//	@Override
//	protected Long getId(Category entity) {
//		return entity.getId();
//	}
//
//}
