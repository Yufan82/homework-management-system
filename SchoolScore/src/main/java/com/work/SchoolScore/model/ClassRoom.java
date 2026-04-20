package com.work.SchoolScore.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "班級")
public class ClassRoom {

	@Schema(description = "班級ID")
	private Long id;

	@Schema(description = "班級名稱", example = "三年甲班")
	private String name;

	@Schema(description = "學年", example = "113")
	private int schoolYear;

	@Schema(description = "學期 (1 或 2)", example = "1")
	private int semester;

	@Schema(description = "是否為目前啟用班級")
	private boolean active;

	public ClassRoom() {
		super();
	}

	public ClassRoom(Long id, String name, int schoolYear, int semester, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.schoolYear = schoolYear;
		this.semester = semester;
		this.active = active;
	}

	
	// Getter & Setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(int schoolYear) {
		this.schoolYear = schoolYear;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	

	
}
