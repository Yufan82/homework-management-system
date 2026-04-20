package com.work.SchoolScore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.work.SchoolScore.dao.AssignmentDAO;
import com.work.SchoolScore.dao.HomeworkStatusDAO;
import com.work.SchoolScore.dto.homework.HomeworkStatusRequest;
import com.work.SchoolScore.model.HomeworkStatus;

@ExtendWith(MockitoExtension.class)
class HomeworkStatusServiceImplTest {

	@Mock
	private HomeworkStatusDAO dao;

	@Mock
	private AssignmentDAO assignmentDao;

	@InjectMocks
	HomeworkStatusServiceImpl service;

	@Test
	public void findByAssignmentId() {

		var spyService = spy(service);

		// 假資料
		HomeworkStatus test = new HomeworkStatus();
		test.setSeatNo(1);

		// 模擬 DAO 行為
		doReturn(List.of(test)).when(dao).findByAssignmentId(1L);

		var result = spyService.findByAssignmentId(1L);

		assertEquals(1, result.size());
		verify(dao).findByAssignmentId(1L);

	}

	@Test
	public void findUnsubmitted() {

		var spyService = spy(service);

		// 假資料
		HomeworkStatus s1 = new HomeworkStatus();
		s1.setSubmitted(true);

		HomeworkStatus s2 = new HomeworkStatus();
		s2.setSubmitted(false);

		doReturn(List.of(s1, s2)).when(dao).findByAssignmentId(1L);

		var result = spyService.findUnsubmitted(1L);

		assertEquals(1, result.size());
		assertFalse(result.get(0).isSubmitted());

		verify(spyService).findUnsubmitted(1L);
	}

	@Test
	public void update() {

		var spyService = spy(service);

		HomeworkStatus test = new HomeworkStatus();
		test.setId(1L);
		test.setSubmitted(false);

		HomeworkStatusRequest req = new HomeworkStatusRequest(true, true);

		doReturn(Optional.of(test)).when(dao).findById(1L);
		doReturn(test).when(dao).save(any());

		var result = spyService.update(1L, req);

		assertTrue(result.isSubmitted());
		assertTrue(result.isCorrected());
		assertNotNull(result.getSubmitTime());
		assertNotNull(result.getCorrectTime());

		verify(dao).save(test);
	}

	@Test
	public void update_noFound() {

		var spyService = spy(service);
		
		HomeworkStatusRequest req = new HomeworkStatusRequest(true, true);

		doReturn(Optional.empty()).when(dao).findById(1L);

		assertThrows(RuntimeException.class, ()->
				spyService.update(1L, req)
				);
	}

}
