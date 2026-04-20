package com.work.SchoolScore.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subject")
@Schema(description = "科目")
public class Subject {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "科目ID", example = "1")
	private Long id;
    
	
	@Column(nullable = false)
    @Schema(description = "科目名稱", example = "國文")
    private String name; // 國文、英文、數學、自然、社會、其他
    
	public Subject() {
	}
	
	// 解決編碼問題
	public Subject(Long id) {
        this.id = id;
    }

	public Subject(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
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

	@Override
	public String toString() {
		return "Subject [id=" + id + ", 科目=" + name + "]";
	}
    
	
    
}
