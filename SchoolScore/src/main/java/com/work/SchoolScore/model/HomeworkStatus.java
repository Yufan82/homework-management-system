package com.work.SchoolScore.model;

import java.time.LocalDateTime;

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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
    name = "homework_status",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"assignment_id", "seat_no"})
    }	// 同一份作業 + 同一個座號只能有一筆
)
@Schema(description = "作業提交 / 訂正狀態")
public class HomeworkStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "狀態ID")
	private Long id;

	@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
	@Schema(description = "所屬作業")
	private Assignment assignment;

	@Min(1)
    @Max(40)
    @Column(name = "seat_no", nullable = false)
	@Schema(description = "座號 (1~40)", example = "12")
	private int seatNo;

	@Column(name = "submitted", nullable = false)
	@Schema(description = "是否已提交")
	private boolean submitted = true; // 預設為已提交

	@Column(name = "corrected", nullable = false)
	@Schema(description = "是否已訂正")
	private boolean corrected;

	@Column(name = "submit_time")
	@Schema(description = "提交時間")
	private LocalDateTime submitTime;

	@Column(name = "correct_time")
	@Schema(description = "訂正時間")
	private LocalDateTime correctTime;

	public HomeworkStatus() {
	}

	public HomeworkStatus(Long id, Assignment assignment, int seatNo, boolean submitted, boolean corrected,
			LocalDateTime submitTime, LocalDateTime correctTime) {
		super();
		this.id = id;
		this.assignment = assignment;
		this.seatNo = seatNo;
		this.submitted = submitted;
		this.corrected = corrected;
		this.submitTime = submitTime;
		this.correctTime = correctTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public int getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}

	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public boolean isCorrected() {
		return corrected;
	}

	public void setCorrected(boolean corrected) {
		this.corrected = corrected;
	}

	public LocalDateTime getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(LocalDateTime submitTime) {
		this.submitTime = submitTime;
	}

	public LocalDateTime getCorrectTime() {
		return correctTime;
	}

	public void setCorrectTime(LocalDateTime correctTime) {
		this.correctTime = correctTime;
	}

	
	// 建立一個 Helper Method 方便取得 ID，避免外部代碼崩潰
    public Long getAssignmentId() {
        return (assignment != null) ? assignment.getId() : null;
    }
	
	@Override
	public String toString() {
		return "HomeworkStatus [id=" + id + ", assignment=" + assignment + ", seatNo=" + seatNo + ", submitted="
				+ submitted + ", corrected=" + corrected + ", submitTime=" + submitTime + ", correctTime=" + correctTime
				+ "]";
	}

}
