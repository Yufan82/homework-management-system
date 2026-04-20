package com.work.SchoolScore.model;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "assignment")
@Schema(description = "作業本體")
public class Assignment {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "作業ID")
	private Long id;

	// --- 修改處：由 Long id 改為 Object 關聯 ---

	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
	@Schema(description = "科目")
	private Subject subject;

	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
	@Schema(description = "作業類別")
	private Category category;
	// ---------------------------------------

	
    @Column(name = "item", nullable = false)
	@Schema(description = "作業名稱 (必填)", example = "第二次習作")
	private String item;

	@Column(name = "page_range", length = 100)
	@Schema(description = "頁碼範圍或單元 (可為 null)", example = "3~10")
	private String pageRange;

	@Column(name = "assign_date")
	@Schema(description = "指派日期(可為 null)")
	private LocalDate assignDate;
	
	// 當刪除作業時，自動刪除關聯的 40 筆狀態 (OrphanRemoval)
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HomeworkStatus> homeworkStatuses;

	public Assignment() {

	}

	public Assignment(Long id, Subject subject, Category category, String item, String pageRange,
			LocalDate assignDate) {
		super();
		this.id = id;
		this.subject = subject;
		this.category = category;
		this.item = item;
		this.pageRange = pageRange;
		this.assignDate = assignDate;
	}

	// Setter & Getter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getPageRange() {
		return pageRange;
	}

	public void setPageRange(String pageRange) {
		this.pageRange = pageRange;
	}

	public LocalDate getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(LocalDate assignDate) {
		this.assignDate = assignDate;
	}

	@Override
	public String toString() {
		return "Assignment [id=" + id + ", subject=" + subject + ", category=" + category + ", item=" + item
				+ ", pageRange=" + pageRange + ", assignDate=" + assignDate + "]";
	}

}
