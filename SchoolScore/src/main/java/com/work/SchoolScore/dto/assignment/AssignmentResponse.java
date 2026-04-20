package com.work.SchoolScore.dto.assignment;

public record AssignmentResponse(

		Long id,

		String subjectName, 
		String categoryName,

		String item,
		String pageRange, 
		String assignDate

) {}
