/**
 * 
 */

// ==========================
// 假資料（模擬後端）
// ==========================
let homeworkData = {};

const subjects = ["國文", "英文"];
const categories = {
    "國文": ["習作"],
    "英文": ["單字"]
};

// 初始化
for (let i = 1; i <= 40; i++) {
    homeworkData[i] = [
        {
            subject: "國文",
            category: "習作",
            name: "作業A",
            submitted: false
        },
        {
            subject: "國文",
            category: "習作",
            name: "作業B",
            submitted: false
        },
        {
            subject: "英文",
            category: "單字",
            name: "作業C",
            submitted: false
        }
    ];
}

// ==========================
// DOM
// ==========================
const seatContainer = document.getElementById("seatContainer");
const resultArea = document.getElementById("resultArea");

let currentSeat = null;
let selectedItem = null;

// Modal
const modal = new bootstrap.Modal(document.getElementById('confirmModal'));
const confirmBtn = document.getElementById("confirmBtn");

// ==========================
// 座號
// ==========================
for (let i = 1; i <= 40; i++) {
    const btn = document.createElement("button");
    btn.innerText = i;
    btn.className = "btn btn-outline-primary seat-btn";

    btn.onclick = () => {
        currentSeat = i;
        renderData();
    };

    seatContainer.appendChild(btn);
}

// ==========================
// 渲染資料（重點）
// ==========================
function renderData() {

    const data = homeworkData[currentSeat];

    // 只抓未交
    const unsubmitted = data.filter(d => !d.submitted);

    if (unsubmitted.length === 0) {
        resultArea.innerHTML = "🎉 全部已繳交";
        return;
    }

    // 分組（subject → category）
    const map = {};

    unsubmitted.forEach(item => {
        if (!map[item.subject]) {
            map[item.subject] = {};
        }

        if (!map[item.subject][item.category]) {
            map[item.subject][item.category] = [];
        }

        map[item.subject][item.category].push(item);
    });

    // 組 HTML
    let html = "";

    for (let subject in map) {
        html += `<div class="subject-title">${subject}</div>`;

        for (let category in map[subject]) {
            html += `<div class="category-title">| ${category} |</div>`;

            map[subject][category].forEach(item => {
                html += `
                    <button class="btn btn-danger assignment-btn"
                        onclick="openModal('${item.name}')">
                        ${item.name}
                    </button>
                `;
            });
        }
    }

    resultArea.innerHTML = html;
}

// ==========================
// 打開 Modal
// ==========================
function openModal(name) {
    selectedItem = homeworkData[currentSeat].find(i => i.name === name);

    document.getElementById("modalText").innerText =
        `確定將「${name}」標記為已繳交嗎？`;

    modal.show();
}

// ==========================
// 確認按鈕
// ==========================
confirmBtn.onclick = () => {
    if (selectedItem) {
        selectedItem.submitted = true;
    }

    modal.hide();
    renderData();
};