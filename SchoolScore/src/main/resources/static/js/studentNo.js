const API_BASE = "http://localhost:8081/api/homework-status";

let currentSeat = null;
let modal = new bootstrap.Modal(document.getElementById('confirmModal'));
let selectedItem = null;

// ==========================
// 座號初始化
// ==========================
const seatContainer = document.getElementById("seatContainer");

for (let i = 1; i <= 40; i++) {
    const btn = document.createElement("button");
    btn.className = "btn btn-outline-primary seat-btn";
    btn.innerText = i;

    btn.onclick = () => loadSeatData(i);

    seatContainer.appendChild(btn);
}

// ==========================
// 載入 API 資料
// ==========================
async function loadSeatData(seatNo) {
    currentSeat = seatNo;

    try {
        const res = await fetch(`${API_BASE}/seat/${seatNo}/unfinished`);
        const data = await res.json();

        renderData(data);

    } catch (err) {
        console.error("API 錯誤", err);
    }
}

// ==========================
// 固定科目排序（重點🔥）
// ==========================
const SUBJECT_ORDER = ["國文", "英文", "數學", "自然", "社會", "其他"];

// ==========================
// 渲染
// ==========================
function renderData(data) {

    const resultArea = document.getElementById("resultArea");

    if (data.length === 0) {
        resultArea.innerHTML = "🎉 全部完成";
        return;
    }

    // 分組
    const map = {};

    data.forEach(item => {
        if (!map[item.subjectName]) {
            map[item.subjectName] = {};
        }

        if (!map[item.subjectName][item.categoryName]) {
            map[item.subjectName][item.categoryName] = [];
        }

        map[item.subjectName][item.categoryName].push(item);
    });

    let html = "";

    // 👉 按固定順序排列
    SUBJECT_ORDER.forEach(subject => {

        if (!map[subject]) return;

        html += `<div class="subject-title">${subject}</div>`;

        Object.keys(map[subject]).forEach(category => {

            html += `<div class="category-title">| ${category} |</div>`;

            map[subject][category].forEach(item => {

                let statusText = !item.submitted ? "未交" : "未訂正";

                html += `
                    <button class="btn btn-danger assignment-btn"
                        onclick='openModal(${JSON.stringify(item)})'>
                        ${item.item} (${statusText})
                    </button>
                `;
            });
        });
    });

    resultArea.innerHTML = html;
}

// ==========================
// 開啟 Modal
// ==========================
function openModal(item) {
    selectedItem = item;

    document.getElementById("modalText").innerText =
        `確定更新「${item.item}」狀態？`;

    modal.show();
}

// ==========================
// 確認更新（呼叫 API）
// ==========================
document.getElementById("confirmBtn").onclick = async () => {

    if (!selectedItem) return;

    let newSubmitted = selectedItem.submitted;
    let newCorrected = selectedItem.corrected;

    // 邏輯（重要🔥）
    if (!selectedItem.submitted) {
        newSubmitted = true;
    } else if (!selectedItem.corrected) {
        newCorrected = true;
    }

    try {
        await fetch(`${API_BASE}/${selectedItem.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                submitted: newSubmitted,
                corrected: newCorrected
            })
        });

        modal.hide();

        // 👉 重新載入（即時更新）
        loadSeatData(currentSeat);

    } catch (err) {
        console.error("更新失敗", err);
    }
};