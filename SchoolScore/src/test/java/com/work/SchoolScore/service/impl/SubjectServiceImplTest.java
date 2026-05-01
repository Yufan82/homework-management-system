package com.work.SchoolScore.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.work.SchoolScore.dao.SubjectDAO;
import com.work.SchoolScore.dto.subject.SubjectRequest;
import com.work.SchoolScore.model.Subject;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {

	@Mock
	private SubjectDAO dao;
	
	@InjectMocks
	private SubjectServiceImpl service;
	
	
	@Test
	public void findAll() {
		
		List<Subject> monList = List.of(
					new Subject(1L,"國文",10)
				);
		
		when(dao.findAll()).thenReturn(monList);
		
		List<Subject> result = service.findAll();
		
		assertEquals(1, result.size());
		verify(dao).findAll();
		 
	}


	@Test
	public void findById_success() {
		
		var s = new Subject();
		s.setId(1L);
		
		when(dao.findById(1L)).thenReturn(Optional.of(s));
		Optional<Subject> result = service.findById(1L);
		
		assertTrue(result.isPresent());
	}
	
	@Test
	public void findById_noFound() {
		
		when(dao.findById(1L)).thenReturn(Optional.empty());
		
		Optional<Subject> result = service.findById(1L);
		
		assertTrue(result.isEmpty());
	}


	@Test
	public void save() {
				
		Subject s = new Subject(1L,"Test",10);
		
		when(dao.save(any(Subject.class))).thenReturn(s);
		
		SubjectRequest req = new SubjectRequest("Test");
		
		var result = service.save(req);
		
		assertNotNull(result);
		assertEquals("Test", result.getName());
		
		verify(dao,times(1)).save(any(Subject.class));
		
	}


	@Test
	public void update() {
		
		// 準備資料
		Subject test = new Subject();
		test.setId(1L);
		test.setName("AAA");
		
		when(dao.findById(1L)).thenReturn(Optional.of(test));
		when(dao.save(any(Subject.class))).thenReturn(test);
		
		SubjectRequest req = new SubjectRequest("BBB");
		
		Optional<Subject> result = service.update(1L, req);
		
		assertEquals("BBB", result.get().getName());
		assertTrue(result.isPresent());
	}


	@Test
	public void delete() {
		
		Subject s = new Subject();
		s.setId(1L);
		
		when(dao.findById(1L)).thenReturn(Optional.of(s));
		
		boolean result = service.delete(1L);
		
		assertTrue(result);
		verify(dao).delete(s);
	}

}
