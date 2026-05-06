const BASE_URL = "http://localhost:8081/api";

// 頁面載入
window.onload = function () {
    loadDashboard();
};

// 主流程
async function loadDashboard() {
    const subjects = await fetchSubjects();
    renderSubjects(subjects);
}

// 取得科目
async function fetchSubjects() {
    const res = await fetch(`${BASE_URL}/subjects`);
    return await res.json();
}

// ⭐ 核心：查詢該科是否有未完成
async function fetchUnfinished(subjectId) {
    const res = await fetch(`${BASE_URL}/homework-status/subject/${subjectId}/unfinished`);
    return await res.json();
}

// 渲染畫面
async function renderSubjects(subjects) {
    const container = document.getElementById("subjectContainer");
    container.innerHTML = "";

    for (const subject of subjects) {

        // 👉 呼叫你現在的 API
        const unfinishedList = await fetchUnfinished(subject.id);

        // 未完成數量
        const count = unfinishedList.length;

        // 建立欄位
        const col = document.createElement("div");
        col.className = "col-md-6";

        const card = document.createElement("div");
        card.className = "subject-card";
        card.onclick = () => goToSubject();

        // ⭐ 有未完成 → 變紅
        if (count > 0) {
            card.classList.add("has-unsubmitted");
        }

        // 科目名稱
        const name = document.createElement("div");
        name.className = "subject-name";
        name.innerText = subject.name;

        card.appendChild(name);

        // ⭐ 顯示未完成數量
        /*if (count > 0) {
            const badge = document.createElement("div");
            badge.className = "badge-unsubmitted";
            badge.innerText = count + "筆未完成";
            card.appendChild(badge);
        } */

        col.appendChild(card);
        container.appendChild(col);
    }
}

// 點擊導頁
function goToSubject() {
    window.location.href = `/Status.html`;
}