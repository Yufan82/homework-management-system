package com.work.SchoolScore.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work.SchoolScore.model.ClassRoom;
import com.work.SchoolScore.service.ClassRoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/classroom")
@Tag(name = "班級 API", description = "班級相關操作")
public class ClassRoomResource {
	
	private final ClassRoomService sub;

	public ClassRoomResource(ClassRoomService sub) {
		this.sub = sub;
	}
	
	@Operation(summary = "獲取班級列表", description = "用於前端下拉選單展示所有可用科目")
	@GetMapping
	public List<ClassRoom> findAll() {
		return sub.findAll();
	}
	
	@Operation(summary = "獲取單個班級", description = "用於選取該班級")
	@GetMapping("/{id}")
	public Optional<ClassRoom> findById(
			@Parameter(description = "選取班級 ID") @PathVariable Long id) {
		return sub.findById(id);
	}

	@Operation(summary = "建立新班級", description = "老師可手動新增如班級")
	@PostMapping
	public ClassRoom add(@RequestBody ClassRoom classroom) {
		return sub.save(classroom);
	}
	
	@Operation(summary = "更改班級", description = "老師可更改班級項目")
	@PutMapping("/{id}")
	public ClassRoom update (@Parameter(description = "選取班級 ID") @PathVariable Long id, @RequestBody ClassRoom room) {
		// 強制設定 ID 以防止 路徑不符
		room.setId(id);
		return sub.update(room);
	}

	@Operation(summary = "刪除班級", description = "根據 ID 刪除指定的班級資料")
	@DeleteMapping("/{id}")
	public void delete(
			@Parameter(description = "欲刪除的班級 ID")	@PathVariable Long id) {
		sub.delete(id);
	}
	
}
