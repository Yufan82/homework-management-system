package com.work.SchoolScore.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work.SchoolScore.dto.homework.HomeworkStatusRequest;
import com.work.SchoolScore.dto.homework.HomeworkStatusResponse;
import com.work.SchoolScore.model.HomeworkStatus;
import com.work.SchoolScore.service.HomeworkStatusService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/homework-status")
@Tag(name = "HomeworkStatus 狀態管理", description = "處理作業提交與訂正狀態的 API")
public class HomeworkStatusResource {

	@Autowired
	private HomeworkStatusService status;

	@Operation(summary = "查詢某作業全部狀態", description = "依據 Assignment ID 取得所有座號(1-40)的狀態列表")
	@GetMapping("/assignment/{assignmentId}")
	public List<HomeworkStatusResponse> getByAssignment(
			@Parameter(description = "選取作業 ID") @PathVariable Long assignmentId) {
		return status.findByAssignmentId(assignmentId)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Operation(summary = "查詢未交名單", description = "篩選出該作業中 submitted 為 false 的紀錄")
	@GetMapping("/assignment/{assignmentId}/unsubmitted")
	public List<HomeworkStatusResponse> getUnsubmitted(@Parameter(description = "選取作業 ID") @PathVariable Long assignmentId) {
		return status.findUnsubmitted(assignmentId)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Operation(summary = "修改單一狀態", description = "根據狀態 ID 更新提交或訂正情況")
	@PutMapping("/{id}")
	public HomeworkStatusResponse updateStatus(@PathVariable Long id, @RequestBody @Valid HomeworkStatusRequest req) {

		var hs = status.update(id, req);

		return toResponse(hs);
	}

	@Operation(summary = "依科目查詢未完成作業", description = "列出該科目所有未繳交或未訂正的作業")
	@GetMapping("/subject/{subjectId}/unfinished")
	public List<HomeworkStatusResponse> getUnfinishedBySubject(@Parameter(description = "科目 ID") @PathVariable Long subjectId) {

		return status.findUnfinishedBySubject(subjectId)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Operation(summary = "查詢某座號未完成作業", description = "列出指定座號所有未繳交或未訂正的作業")
	@GetMapping("/seat/{seatNo}/unfinished")
	public List<HomeworkStatusResponse> getUnfinishedBySeat(@Parameter(description = "學生座號 (1~40)") @PathVariable int seatNo) {

		return status.findUnfinishedBySeatNo(seatNo)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	private HomeworkStatusResponse toResponse(HomeworkStatus hs) {

		return new HomeworkStatusResponse(
				
				hs.getId(),
				hs.getSeatNo(),
				hs.isSubmitted(), 
				hs.isCorrected(),

				// 展開 Assignment
				hs.getAssignment().getSubject().getName(),
				hs.getAssignment().getCategory().getName(),
				hs.getAssignment().getItem(),
				hs.getAssignment().getPageRange(),
				hs.getAssignment().getAssignDate(),

				hs.getSubmitTime(),
				hs.getCorrectTime());
	}

}
