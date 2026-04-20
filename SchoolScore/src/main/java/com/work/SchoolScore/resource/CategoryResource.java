package com.work.SchoolScore.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work.SchoolScore.dto.category.CategoryRequest;
import com.work.SchoolScore.dto.category.CategoryResponse;
import com.work.SchoolScore.model.Category;
import com.work.SchoolScore.model.Subject;
import com.work.SchoolScore.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "作業類別 API", description = "管理科目底下的作業類型（如習作、單字本）")
public class CategoryResource {
	
	@Autowired
	private  CategoryService service;

	public CategoryResource(CategoryService service) {
		this.service = service;
	}
	
	@Operation(summary = "獲取所有類別")
    @GetMapping
    public List<CategoryResponse> list() {
        return service.findAll()
        		.stream()
        		.map(this::ToResponse)
        		.toList();
    }
	
	@Operation(summary = "依科目 ID 篩選類別", description = "當老師選定『國文』時，僅顯示國文相關類別")
    @GetMapping("/subject/{subjectId}")
    public List<CategoryResponse> listBySubject(
    		@Parameter(description = "選取科目 ID") @PathVariable Long subjectId) {
        return service.findBySubject(subjectId)
        		.stream()
        		.map(this::ToResponse)
        		.toList();
    }
	
	@Operation(summary = "建立新類別")
    @PostMapping
    public CategoryResponse add(@RequestBody CategoryRequest req) {
		
		Category category = new Category();
		
		category.setName(req.name());
		category.setSubject( new Subject(req.subjectId()) );
		
		var result = service.save(category);
        return ToResponse(result);
    }
	
	
	
	
	@Operation(summary = "修改類別")
    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id, @RequestBody CategoryRequest req) {
        
		Category category = new Category();
		
		category.setId(id);
		category.setName(req.name());
		category.setSubject( new Subject(req.subjectId()) );
		
		var result = service.update(category);
		
        return (result != null)? ToResponse(result) : null;
    }
	
	@Operation(summary = "刪除類別")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted: " + id;
    }
	
	// 轉換邏輯：Entity -> DTO
	private CategoryResponse ToResponse (Category c) {
		
		return new CategoryResponse(
					c.getId(),
					c.getName(),
					c.getSubject() !=null? c.getSubject().getId() : null,
					c.getSubject() !=null? c.getSubject().getName() : null
				);
	}
	
}
