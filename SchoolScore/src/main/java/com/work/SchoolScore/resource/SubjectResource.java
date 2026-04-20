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

import com.work.SchoolScore.dto.subject.SubjectRequest;
import com.work.SchoolScore.dto.subject.SubjectResponse;
import com.work.SchoolScore.model.Subject;
import com.work.SchoolScore.service.SubjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/subjects")
@Tag(name = "科目 API", description = "科目相關操作")
public class SubjectResource {

	@Autowired
	private final SubjectService sub;

	public SubjectResource(SubjectService sub) {
		this.sub = sub;
	}

	@Operation(summary = "獲取所有科目列表", description = "用於前端下拉選單展示所有可用科目")
	@GetMapping
	public List<SubjectResponse> list() {

		return sub.findAll().stream().map(this::ToResponse).toList();
	}

	@Operation(summary = "獲取單個科目", description = "用於選取該科目")
	@GetMapping("/{id}")
	public ResponseEntity<SubjectResponse> findById(@Parameter(description = "選取科目 ID") @PathVariable Long id) {

		return sub.findById(id).map(s -> ResponseEntity.ok(ToResponse(s)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Operation(summary = "建立新科目", description = "老師可手動新增如：國文、英文、數學等科目選項")
	@PostMapping
	public SubjectResponse add(@RequestBody SubjectRequest subject) {
		var result = sub.save(subject);
		return ToResponse(result);
	}

	@Operation(summary = "更改科目", description = "老師可更改科目")
	@PutMapping("/{id}")
	public ResponseEntity<SubjectResponse> update(@PathVariable Long id, @RequestBody SubjectRequest subject) {
		
		return sub.update(id, subject)
				.map(r-> ResponseEntity.ok(ToResponse(r)))
				.orElseGet( ()->ResponseEntity.notFound().build() );
		
	}

	@Operation(summary = "刪除科目", description = "根據 ID 刪除指定的科目資料")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "欲刪除的科目 ID") @PathVariable Long id) {
        
    		return sub.delete(id)?
    				ResponseEntity.noContent().build():
    					ResponseEntity.notFound().build();
    	
    }

	/**
	 * 私有輔助方法：將 Subject Entity 轉換為 SubjectResponse DTO
	 */
	private SubjectResponse ToResponse(Subject subject) {
		return new SubjectResponse(subject.getId(), subject.getName());
	}
}
