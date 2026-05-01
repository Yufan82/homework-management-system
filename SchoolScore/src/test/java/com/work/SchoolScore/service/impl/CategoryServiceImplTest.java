package com.work.SchoolScore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.work.SchoolScore.dao.CategoryDAO;
import com.work.SchoolScore.dao.SubjectDAO;
import com.work.SchoolScore.model.Category;
import com.work.SchoolScore.model.Subject;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
	@Mock
	private CategoryDAO dao;

	@Mock
	private SubjectDAO subjectDao;

	@InjectMocks
	private CategoryServiceImpl service;

	@Test
	public void findAll() {

		// 假資料
		Subject subject = new Subject();
		subject.setId(1L);

		List<Category> moList = List.of(

				new Category(1L, "AAA", subject,10)

		);

		// 模擬 DAO 行為
		when(dao.findAll()).thenReturn(moList);
		when(subjectDao.findById(1L)).thenReturn(Optional.of(subject));

		// 執行
		List<Category> result = service.findAll();

		// 驗證
		assertEquals(1, result.size());
		verify(dao).findAll();
		verify(subjectDao).findById(1L);
	}

	@Test
	public void findBySubject_success() {

		// 假資料
		Long subjectId = 1L;

		List<Category> moList = List.of(new Category());

		// 模擬 DAO 行為
		when(dao.findBySubjectId(subjectId)).thenReturn(moList);

		// 執行
		var result = service.findBySubject(subjectId);

		// 驗證
		assertEquals(1, result.size());
		verify(dao).findBySubjectId(subjectId);
	}

	@Test
	public void findBySubject_Empty() {

		// 假資料
		Long subjectId = 99L;

		// 模擬 DAO 行為
		when(dao.findBySubjectId(subjectId)).thenReturn(List.of());

		// 執行
		var result = service.findBySubject(subjectId);

		// 驗證
		assertEquals(0, result.size());
		assertTrue(result.isEmpty());
		assertNotNull(result);

		verify(dao).findBySubjectId(subjectId);
	}

	@Test
	public void save() {

		// 假資料
		Subject subject = new Subject();
		subject.setId(1L);

		Category category = new Category();
		category.setSubject(subject);

		// 模擬 DAO
		when(subjectDao.findById(1L)).thenReturn(Optional.of(subject));
		when(dao.save(category)).thenReturn(category);

		// 執行
		var result = service.save(category);

		// 驗證
		assertNotNull(result);

		verify(subjectDao).findById(1L);
		verify(dao).save(category);
	}
	
	@Test
	public void save_fail() {

		// 假資料
		Subject subject = new Subject();
		subject.setId(1L);

		Category category = new Category();
		category.setSubject(subject);

		// 模擬 DAO
		when(subjectDao.findById(1L)).thenReturn(Optional.empty());

		// 執行
		assertThrows(RuntimeException.class, () -> {
	        service.save(category);
	    });


		// 驗證
		verify(subjectDao).findById(1L);
	}
	
	

	@Test
	public void update() {
		
		Category test = new Category();
		test.setId(1L);
		test.setName("AAA");
		
		Category update = new Category();
		update.setId(1L);
		update.setName("BBB");

		when(dao.findById(1L)).thenReturn(Optional.of(test));
		when(dao.save(any())).thenReturn(test);
		
		var result = service.update(update);
		
		assertEquals("BBB", result.getName());
		
		verify(dao).findById(1L);
		verify(dao).save(any());
	}

	@Test
	public void delete() {
		
		Category test = new Category();
		test.setId(1L);
		
		when(dao.findById(1L)).thenReturn(Optional.of(test));
		
		boolean result = service.delete(1L);
		
		assertTrue(result);
		verify(dao).delete(test);
	}

}
