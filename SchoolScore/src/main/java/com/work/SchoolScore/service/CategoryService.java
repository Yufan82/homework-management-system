package com.work.SchoolScore.service;

import java.util.List;

import com.work.SchoolScore.model.Category;

public interface CategoryService {
	
	List<Category> findAll();
	
    List<Category> findBySubject(Long subjectId);
    
    Category save(Category category);
    
    Category update(Category entity);
    
    boolean delete(Long id);

	
}
