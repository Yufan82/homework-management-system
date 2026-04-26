/**
 * 學生作業提交系統 - 核心邏輯
 * 修正重點：強制過濾已繳交 (submitted: true) 的資料，僅顯示未繳按鈕
 */

const API_BASE = '/api/homework-status';

document.addEventListener('DOMContentLoaded', () => {
	initSubjects(); // 1. 頁面載入後初始化科目導覽
});

// [1] 初始化科目標籤 (動態從 API 抓取)
async function initSubjects() {
	try {
		const response = await fetch('/api/subjects');
		const subjects = await response.json();
		const tabEl = document.getElementById('subjectTab');

		tabEl.innerHTML = subjects.map((s, i) => `
            <li class="nav-item">
                <button class="nav-link ${i === 0 ? 'active' : ''}" 
                        onclick="loadData(${s.id}, this)">${s.name}</button>
            </li>
        `).join('');

		if (subjects.length > 0) loadData(subjects[0].id);
	} catch (error) {
		console.error("無法初始化科目列表:", error);
	}
}

// [2] 載入該科目資料
async function loadData(subjectId, btnElement = null) {
	if (btnElement) {
		document.querySelectorAll('.nav-link').forEach(el => el.classList.remove('active'));
		btnElement.classList.add('active');
	}

	const container = document.getElementById('assignmentContent');
	container.innerHTML = '<div class="text-center p-5">查詢資料中...</div>';

	try {
		// 調用後端「未完成」API：/subject/{id}/unfinished
		const response = await fetch(`${API_BASE}/subject/${subjectId}/unfinished`);
		if (!response.ok) throw new Error('連線伺服器失敗');

		const rawData = await response.json();

		// 核心修正：在此處進行二次過濾，確保 submitted 為 false
		const unsubmittedData = rawData.filter(item => item.submitted === false);

		// 分組並渲染
		const groupedData = groupDataByAssignment(unsubmittedData);
		renderUI(groupedData);
	} catch (error) {
		container.innerHTML = `<div class="alert alert-danger">載入失敗: ${error.message}</div>`;
	}
}

// [3] 分組邏輯：將扁平 DTO 依照「類別」與「作業」分類
function groupDataByAssignment(data) {
	const groups = {};

	data.forEach(item => {
		const cat = item.categoryName;
		const task = item.item;

		if (!groups[cat]) groups[cat] = {};
		if (!groups[cat][task]) {
			groups[cat][task] = {
				name: task,
				category: cat,
				unsubmitted: []
			};
		}
		// 存入座號資訊
		groups[cat][task].unsubmitted.push({
			id: item.id,
			seatNo: item.seatNo
		});
	});

	return Object.values(groups).flatMap(catObj => Object.values(catObj));
}

// [4] 渲染 UI：生成 HTML 結構
function renderUI(groupedData) {
	const container = document.getElementById('assignmentContent');
	if (groupedData.length === 0) {
		container.innerHTML = '<div class="text-center text-muted p-5">✨ 所有學生皆已繳交</div>';
		return;
	}

	container.innerHTML = groupedData.map(group => `
        <div class="category-group mb-4">
            <h5 class="fw-bold text-primary mb-3">
                <span class="badge bg-primary me-2">${group.category}</span> ${group.name}
            </h5>
            <div class="assignment-row shadow-sm bg-white p-3 rounded border">
                <div class="d-flex flex-wrap">
                    ${group.unsubmitted.sort((a, b) => a.seatNo - b.seatNo).map(st => `
                        <button class="btn btn-outline-primary seat-btn rounded-circle m-1" 
                                style="width: 45px; height: 45px;"
                                onclick="markAsSubmitted(${st.id}, ${st.seatNo}, this)">
                            ${st.seatNo}
                        </button>
                    `).join('')}
                </div>
            </div>
        </div>
    `).join('');
}

// [5] 變更狀態：點擊後變更為已繳交並「隱藏」按鈕
async function markAsSubmitted(statusId, seatNo, btn) {
	if (!confirm(`確認座號 ${seatNo} 已繳交？`)) return;

	try {
		const response = await fetch(`${API_BASE}/${statusId}`, {
			method: 'PUT',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({
				submitted: true,   // 更新為已繳交
				corrected: false
			})
		});

		if (response.ok) {
			// 執行消失動畫
			btn.style.transition = '0.3s';
			btn.style.transform = 'scale(0)';
			btn.style.opacity = '0';

			setTimeout(() => {
				const parent = btn.parentElement;
				btn.remove();
				// 如果這項作業所有人都交了，移除整行
				if (parent.children.length === 0) {
					parent.closest('.category-group').remove();
					// 如果整個畫面都空了，顯示完成訊息
					if (document.getElementById('assignmentContent').children.length === 0) {
						document.getElementById('assignmentContent').innerHTML =
							'<div class="text-center text-muted p-5">✨ 所有學生皆已繳交</div>';
					}
				}
			}, 300);
		} else {
			alert('更新失敗，請檢查 API 狀態');
		}
	} catch (error) {
		alert("連線錯誤：" + error.message);
	}
}