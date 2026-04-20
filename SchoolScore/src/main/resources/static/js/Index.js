/**
 * 
 */
// API Base（之後可抽 config）
const BASE_URL = "http://localhost:8081/api";

// 頁面載入
window.onload = function() {
	loadDashboard();
};

// 主流程
async function loadDashboard() {
	const subjects = await fetchSubjects();
	renderSubjects(subjects);
}

// 1️⃣ 取得科目
async function fetchSubjects() {
	const res = await fetch(`${BASE_URL}/subjects`);
	return await res.json();
}

// 2️⃣ 取得某科未交狀態
async function fetchUnsubmitted(subjectId) {
	const res = await fetch(`${BASE_URL}/homework-status/unsubmitted/subject/${subjectId}`);
	return await res.json();
}

// 3️⃣ 渲染畫面
async function renderSubjects(subjects) {
	const container = document.getElementById("subjectContainer");
	container.innerHTML = "";

	for (const subject of subjects) {

		// 👉 查詢未交資料
		const status = await fetchUnsubmitted(subject.id);

		const count = status.unsubmittedSeats
			? status.unsubmittedSeats.length
			: 0;

		// 建立卡片
		const col = document.createElement("div");
		col.className = "col-md-6";

		const card = document.createElement("div");
		card.className = "subject-card";
		card.onclick = () => goToSubject(subject.id);

		// 有未交 → 加樣式
		if (count > 0) {
			card.classList.add("has-unsubmitted");
		}

		// 名稱
		const name = document.createElement("div");
		name.className = "subject-name";
		name.innerText = subject.name;

		card.appendChild(name);

		// 未交 badge
		if (count > 0) {
			const badge = document.createElement("div");
			badge.className = "badge-unsubmitted";
			badge.innerText = count + "人未交";
			card.appendChild(badge);
		}

		col.appendChild(card);
		container.appendChild(col);
	}
}

// 點擊導頁
function goToSubject(subjectId) {
	window.location.href = `/assignment.html?subjectId=${subjectId}`;
}