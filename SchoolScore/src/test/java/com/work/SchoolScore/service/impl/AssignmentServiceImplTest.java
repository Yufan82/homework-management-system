package com.work.SchoolScore.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.mockito.junit.jupiter.MockitoExtension;

import com.work.SchoolScore.dao.AssignmentDAO;
import com.work.SchoolScore.dao.CategoryDAO;
import com.work.SchoolScore.dao.HomeworkStatusDAO;
import com.work.SchoolScore.dao.SubjectDAO;
import com.work.SchoolScore.dto.assignment.AssignmentRequest;
import com.work.SchoolScore.model.Assignment;
import com.work.SchoolScore.model.Category;
import com.work.SchoolScore.model.HomeworkStatus;
import com.work.SchoolScore.model.Subject;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceImplTest {

	@InjectMocks
	AssignmentServiceImpl service;

	@Mock
	private AssignmentDAO dao;

	@Mock
	private SubjectDAO subjectDAO;

	@Mock
	private CategoryDAO categoryDAO;

	@Mock
	private HomeworkStatusDAO homeworkStatusDAO;

	@Test
	public void save() {

		// 1. 準備資料
		AssignmentRequest req = new AssignmentRequest(
				1L, // subjectId
				1L, // categoryId
				"作業1", "1-10", null, List.of(2, 5, 10) // 未交

		);

		Subject subject = new Subject();
		subject.setId(1L);

		Category category = new Category();
		category.setId(1L);

		Assignment saveAssignment = new Assignment();
		saveAssignment.setId(100L);

		// 2. 測試 Mock
		when(subjectDAO.findById(1L)).thenReturn(Optional.of(subject));
		when(categoryDAO.findById(1L)).thenReturn(Optional.of(category));
		when(dao.save(any(Assignment.class))).thenReturn(saveAssignment);

		// 3. 執行
		Assignment result = service.save(req);

		// 4. 驗證
		assertNotNull(result); 
		assertEquals(100L, result.getId());

		// 驗證 Assignment 是否有存取
		verify(dao, times(1)).save(any(Assignment.class));

		// 驗證 HomeworkStatus 是否建立四十次
		verify(homeworkStatusDAO, times(40)).save(any(HomeworkStatus.class));

//		var spyService = spy(service);

	}

	@Test
	public void update() {
		
		// 準備 舊資料
		Assignment test = new Assignment();
		test.setId(10L);
		
		// 準備 新資料
		AssignmentRequest req = new AssignmentRequest(
				1L, // subjectId
				1L, // categoryId
				"作業1", "1-10", null, List.of(2, 5, 10) // 未交

		);
		
		// 模擬 DAO：當 Service 去查這些 ID 時，回傳準備好的物件
		when(dao.findById(10L)).thenReturn(Optional.of(test));
		when(subjectDAO.findById(1L)).thenReturn(Optional.of(new Subject()));
		when(categoryDAO.findById(1L)).thenReturn(Optional.of(new Category()));
		when(dao.save(any())).thenAnswer(i -> i.getArgument(0)); // 讓 save 回傳傳入的同一個物件
		
		// 宣告捕捉器
		
		@SuppressWarnings("unchecked") // 關閉泛型型別不安全的警告（但風險仍在）
		ArgumentCaptor<List<HomeworkStatus>> captor =
			    ArgumentCaptor.forClass(List.class);
		
		// 執行
		Optional<Assignment> result = service.update(10L, req);
		
		// 驗證
		assertTrue(result.isPresent());
		
		Assignment updated = result.get();

		assertEquals("作業1", updated.getItem());
		assertEquals("1-10", updated.getPageRange());
		
		// 驗證 是否刪除舊名單
		verify(homeworkStatusDAO).deleteByAssignmentId(10L);
		
		// 驗證 saveAll 並抓取參數
		verify(homeworkStatusDAO).saveAll( captor.capture());
		List<HomeworkStatus> list = captor.getValue();
		
		assertEquals(40, list.size());
	}
	
	@Test
	public void findAll() {

		service.findAll();

		verify(dao).findAll();
	}

	@Test
	public void findById_success() {

		var a = new Assignment();
		a.setId(1L);

		when(dao.findById(1L)).thenReturn(Optional.of(a));
		Optional<Assignment> result = service.findById(1L);

		assertTrue(result.isPresent());
	}

	@Test
	public void findById_noFound() {

		// 回傳 為空
		when(dao.findById(1L)).thenReturn(Optional.empty());

		// 驗證
		Optional<Assignment> result = service.findById(1L);
		
		assertTrue(result.isEmpty());
	}

	@Test
	public void delete() {

		Assignment a = new Assignment();
		a.setId(1L);
		
		when(dao.findById(1L)).thenReturn(Optional.of(a));
		
		boolean result = service.delete(1L);
		
		assertTrue(result);
		verify(dao).delete(a);
	}

	

}
