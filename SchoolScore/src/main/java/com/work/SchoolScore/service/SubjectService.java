package com.work.SchoolScore.service;

import java.util.List;
import java.util.Optional;

import com.work.SchoolScore.dto.subject.SubjectRequest;
import com.work.SchoolScore.model.Subject;

public interface SubjectService {

	List<Subject> findAll();

	Optional<Subject> findById(Long id);

	Subject save(SubjectRequest req);
	
	Optional<Subject> update(Long id, SubjectRequest entity);

    boolean delete(Long id);

	
}
