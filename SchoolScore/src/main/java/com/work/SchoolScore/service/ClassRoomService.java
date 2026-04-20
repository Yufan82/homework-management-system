package com.work.SchoolScore.service;

import java.util.List;
import java.util.Optional;

import com.work.SchoolScore.model.ClassRoom;

public interface ClassRoomService {
	
	List<ClassRoom> findAll();

	Optional<ClassRoom> findById(Long id);

	ClassRoom save(ClassRoom entity);
	
	ClassRoom update(ClassRoom entity);

    void delete(Long id);
}
