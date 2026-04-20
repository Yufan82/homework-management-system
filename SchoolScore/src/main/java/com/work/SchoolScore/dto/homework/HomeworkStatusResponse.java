package com.work.SchoolScore.dto.homework;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record HomeworkStatusResponse(
		
		Long id,
	    int seatNo,
	    boolean submitted,
	    boolean corrected,
	    
	    // 攤開 Assignment 
	    String subjectName,   // 例如：國文
	    String categoryName,  // 例如：習作
	    String item,          // 例如：第二次習作
	    String pageRange,     // 頁碼 [cite: 4]
	    LocalDate assignDate, // 作業日期 [cite: 4]
	    
	    // 狀態時間
	    LocalDateTime submitTime,
	    LocalDateTime correctTime
		
		) {}
