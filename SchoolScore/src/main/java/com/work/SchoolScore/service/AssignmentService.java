package com.work.SchoolScore.service;

import java.util.List;
import java.util.Optional;

import com.work.SchoolScore.dto.assignment.AssignmentRequest;
import com.work.SchoolScore.model.Assignment;

public interface AssignmentService {

	List<Assignment> findAll();
	
	Optional<Assignment> findById(Long id);
	
	Assignment  save(AssignmentRequest req);
	
	Optional<Assignment> update(Long id,AssignmentRequest req);
	
	boolean delete(Long id);
	
}
