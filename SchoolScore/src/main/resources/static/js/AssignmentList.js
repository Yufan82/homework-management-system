/**
 *  作業列表 
 */

// 輸入 資料
const API_URL = "/api/assignments";

// 初始化
document.addEventListener("DOMContentLoaded", () => {
	loadAssignments();
});

// 讀取作業列表
function loadAssignments() {
	fetch(API_URL)
		.then(res => res.json())
		.then(data => renderAssignments(data))
		.catch(err => console.error("載入失敗:", err));
}

function renderAssignments(data) {
	const container = document.getElementById("assignmentContainer");
	container.innerHTML = "";

	// 👉 分組（Subject -> Category）
	const grouped = {};

	data.forEach(a => {
		if (!grouped[a.subjectName]) {
			grouped[a.subjectName] = {};
		}

		if (!grouped[a.subjectName][a.categoryName]) {
			grouped[a.subjectName][a.categoryName] = [];
		}

		grouped[a.subjectName][a.categoryName].push(a);
	});

	// 👉 畫畫面
	Object.keys(grouped).forEach(subject => {

		const subjectDiv = document.createElement("div");
		subjectDiv.className = "subject-box";
		subjectDiv.innerHTML = `<h5>📘 ${subject}</h5>`;

		const categories = grouped[subject];

		Object.keys(categories).forEach(category => {

			const categoryDiv = document.createElement("div");
			categoryDiv.className = "category-box";
			categoryDiv.innerHTML = `<strong>📂 ${category}</strong>`;

			categories[category].forEach(a => {

				const row = document.createElement("div");
				row.className = "assignment-row";

				row.innerHTML = `
                    <div>
                        <div><strong>${a.item}</strong></div>
                        <small>頁碼: ${a.pageRange || "-"} | 日期: ${a.assignDate || "-"}</small>
                    </div>
                    <div>
                        <button class="btn btn-sm btn-primary" onclick="edit(${a.id})">
                            修改
                        </button>
                        <button class="btn btn-sm btn-danger" onclick="removeAssignment(${a.id})">
                            刪除
                        </button>
                    </div>
                `;

				categoryDiv.appendChild(row);
			});

			subjectDiv.appendChild(categoryDiv);
		});

		container.appendChild(subjectDiv);
	});
}

// 修改
function edit(id) {
	alert("修改 Assignment ID: " + id);
}

// 刪除
function remove(id) {
	if (confirm("確定要刪除嗎？")) {
		alert("已刪除 ID: " + id);
	}
}

// 初始化
renderAssignments();