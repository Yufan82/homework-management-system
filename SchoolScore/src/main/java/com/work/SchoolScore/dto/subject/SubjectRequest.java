package com.work.SchoolScore.dto.subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SubjectRequest(

		@NotBlank(message = "科目名稱不能為空") 
		@Size(max = 50) 
		String name
		) {
}
