package com.work.SchoolScore.dto.category;


public record CategoryResponse(
		
		Long id,
	    String name,
	    Long subjectId,    // 直接放 ID
	    String subjectName // 直接放名稱    
	    
		) {}
