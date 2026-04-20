package com.work.SchoolScore.dto.assignment;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssignmentRequest(

		@NotNull(message = "必須選擇科目") Long subjectId,

		@NotNull(message = "必須選擇類別") Long categoryId,

		@NotBlank(message = "作業項目名稱不能為空") String item,

		String pageRange,
		
		LocalDate assignDate,

		List<Integer> unsubmittedSeats  // 重點，接收未繳號碼

) {
}
