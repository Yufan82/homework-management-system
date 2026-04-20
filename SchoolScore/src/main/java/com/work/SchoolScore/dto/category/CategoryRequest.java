package com.work.SchoolScore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequest(

		@NotBlank(message = "類別名稱不能為空")
	    @Size(max = 50)
		String name,
		
		@NotNull(message = "必須指定科目")
		Long subjectId
		
		) {}
