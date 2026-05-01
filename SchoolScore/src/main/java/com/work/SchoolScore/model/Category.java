package com.work.SchoolScore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
@Schema(description = "作業類別")
public class Category {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "作業類別ID", example = "1")
	private Long id;

	@Column(name = "name", length = 50, nullable = false)
	@Schema(description = "作業類別名稱", example = "習作")
	private String name; // 習作、作業本…

	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false) // 對齊 Liquibase 外鍵欄位
	@Schema(description = "所屬科目")
	private Subject subject; // Model 關聯：直接引用 Subject 物件
    
    @Column(name = "order_no")
	@Schema(description = "作業排序", example = "10")
	private Integer orderNo;

	public Category() {
	}

	public Category(Long id, String name, Subject subject, Integer orderNo) {
		super();
		this.id = id;
		this.name = name;
		this.subject = subject;
		this.orderNo = orderNo;
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

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", subject=" + subject + ", orderNo=" + orderNo + "]";
	}

	
	
	
}
