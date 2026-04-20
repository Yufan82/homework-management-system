package com.work.SchoolScore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.SchoolScore.dao.CategoryDAO;
import com.work.SchoolScore.dao.SubjectDAO;
import com.work.SchoolScore.model.Category;
import com.work.SchoolScore.service.CategoryService;

//這是 Service 的 實作
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDAO dao;
	
	@Autowired
	private SubjectDAO subjectDao; // 注入 SubjectDAO 以獲取名稱

	public CategoryServiceImpl(CategoryDAO dao, SubjectDAO subjectDao) {
		super();
		this.dao = dao;
		this.subjectDao = subjectDao;
	}

	// 搜尋全部
	@Override
	public List<Category> findAll() {
		List<Category> categories = dao.findAll();
		
        // 補全每個 Category 中的 Subject 資訊
        categories.forEach(this::enrichSubject);
        
        return categories;
	}
	
	// 輔助方法：根據 ID 從 SubjectDAO 撈出完整資料
    private void enrichSubject(Category category) {
        if (category.getSubject() != null && category.getSubject().getId() != null) {
            subjectDao.findById(category.getSubject().getId())
                      .ifPresent(category::setSubject);
        }
    }

    // 根據科目 ID 查詢該科目下的所有類別
	// 實作 CategoryDAO 介面要求的篩選功能
	@Override
	public List<Category> findBySubject(Long subjectId) {
		return dao.findBySubjectId(subjectId);
	}

	// 新增
	@Override
	public Category save(Category entity) {
		
		// 確保關聯的科目是存在的（商業邏輯檢查）
        if (entity.getSubject() != null && entity.getSubject().getId() != null) {
            subjectDao.findById(entity.getSubject().getId())
                      .ifPresentOrElse(
                          entity::setSubject, 
                          () -> { throw new RuntimeException("找不到指定的科目"); }
                      );
        }
		
		return dao.save(entity);
	}

	// 修改
	@Override
	public Category update(Category entity) {
		
		// 建議增加對 ID 的非空檢查
		if (entity.getId() == null)
			return null;

		return dao.findById(entity.getId()).map(existing -> {
			existing.setName(entity.getName());
			
			// 如果有換科目，更新科目關聯
            if (entity.getSubject() != null) {
                existing.setSubject(entity.getSubject());
            }
			
			return dao.save(entity);
		}).orElse(null); // 若找不到該 ID，返回 null
	}

	// 刪除
	@Override
	public boolean delete(Long id) {
		
		return dao.findById(id).map(o -> {
			dao.delete(o);
			return true;
		}).orElse(false);
	}

}
