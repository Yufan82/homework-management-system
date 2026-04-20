package com.work.SchoolScore.dto.homework;

import jakarta.validation.constraints.NotNull;

public record HomeworkStatusRequest(
		
		@NotNull(message = "是否繳交不可為空")
	    Boolean submitted,

	    @NotNull(message = "是否訂正不可為空")
	    Boolean corrected
		
		) {}
