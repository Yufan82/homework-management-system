package com.work.SchoolScore.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work.SchoolScore.dto.assignment.AssignmentRequest;
import com.work.SchoolScore.dto.assignment.AssignmentResponse;
import com.work.SchoolScore.model.Assignment;
import com.work.SchoolScore.service.AssignmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/assignments")
@Tag(name = "作業主體 API", description = "管理作業主體")
public class AssignmentResource {

	@Autowired
	private AssignmentService ass;

	public AssignmentResource(AssignmentService ass) {
		this.ass = ass;
	}

	@Operation(summary = "獲取所有作業")
	@GetMapping
	public List<AssignmentResponse> list() {
		return ass.findAll().stream().map(this::ToResponse).toList();
	}

	@Operation(summary = "獲取單一作業")
	@GetMapping("/{id}")
	public ResponseEntity<AssignmentResponse> findById(@Parameter(description = "選取作業 ID") @PathVariable Long id) {
		return ass.findById(id)
				.map(a -> ResponseEntity.ok(ToResponse(a)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Operation(summary = "新增作業")
	@PostMapping
	public AssignmentResponse add( @Valid @RequestBody AssignmentRequest req) {
		var result = ass.save(req);
		
		System.out.println(req.toString());
		
		return ToResponse(result);
	}

	@Operation(summary = "修改作業")
	@PutMapping("/{id}")
	public ResponseEntity<AssignmentResponse> update(@Valid @PathVariable Long id, @RequestBody AssignmentRequest req) {
		return ass.update(id, req)
				.map(r -> ResponseEntity.ok(ToResponse(r)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Operation(summary = "刪除作業")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@Parameter(description = "選取刪除 ID") @PathVariable Long id) {

		return ass.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}

	/**
	 * 封裝轉換邏輯：Entity -> Response DTO
	 */
	private AssignmentResponse ToResponse(Assignment a) {
		return new AssignmentResponse(
				a.getId(), 
				a.getSubject().getName(),
				a.getCategory().getName(), 
				a.getItem(),
				a.getPageRange(),
				a.getAssignDate() != null ? a.getAssignDate().toString() : null);
	}

}
