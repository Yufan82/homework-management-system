/**
 * 
 */

// 根據 Resource 定義 API 基礎路徑
const API_BASE = '/api/homework-status';

document.addEventListener('DOMContentLoaded', () => {
    initSubjects();
});

// 1. 初始化科目標籤 (假設 Subject ID 如下，實務上建議由 API 取得)
function initSubjects() {
    const subjects = [
        { id: 1, name: "國文" },
        { id: 2, name: "英文" },
        { id: 3, name: "數學" },
        { id: 4, name: "自然" },
        { id: 5, name: "社會" }
    ];
    const tabEl = document.getElementById('subjectTab');
    
    tabEl.innerHTML = subjects.map((s, i) => `
        <li class="nav-item">
            <button class="nav-link ${i===0?'active':''}" onclick="loadData(${s.id}, this)">${s.name}</button>
        </li>
    `).join('');
    
    loadData(subjects[0].id); 
}

// 2. 載入該科目未完成作業 [對應：GET /api/homework-status/subject/{subjectId}/unfinished]
async function loadData(subjectId, btnElement = null) {
    if(btnElement) {
        document.querySelectorAll('.nav-link').forEach(el => el.classList.remove('active'));
        btnElement.classList.add('active');
    }

    const container = document.getElementById('assignmentContent');
    container.innerHTML = '<div class="text-center p-5">載入中...</div>';

    try {
        const response = await fetch(`${API_BASE}/subject/${subjectId}/unfinished`);
        if (!response.ok) throw new Error('無法取得資料');
        
        const rawData = await response.json();
        
        // 格式化資料：將扁平的 HomeworkStatus 列表依 Assignment 進行分組
        const groupedData = groupDataByAssignment(rawData);
        renderUI(groupedData);
    } catch (error) {
        container.innerHTML = `<div class="alert alert-danger">錯誤：${error.message}</div>`;
    }
}

// 分組邏輯
function groupDataByAssignment(data) {
    const groups = {};
    data.forEach(item => {
        const asm = item.assignment;
        if (!groups[asm.id]) {
            groups[asm.id] = {
                name: asm.item, // 依據文件中 Assignment 的 item 欄位 
                category: asm.category.name,
                unsubmitted: []
            };
        }
        groups[asm.id].unsubmitted.push({
            id: item.id,
            seatNo: item.seatNo
        });
    });
    return Object.values(groups);
}

// 3. 渲染 UI
function renderUI(groupedData) {
    const container = document.getElementById('assignmentContent');
    if (groupedData.length === 0) {
        container.innerHTML = '<div class="text-center text-muted p-5">目前無未繳作業</div>';
        return;
    }

    container.innerHTML = groupedData.map(group => `
        <div class="category-group">
            <h5 class="fw-bold text-primary mb-3">類別：${group.category}</h5>
            <div class="assignment-row shadow-sm">
                <div class="assignment-title">${group.name}</div>
                <div class="ms-3 d-flex flex-wrap">
                    ${group.unsubmitted.map(st => `
                        <button class="btn btn-outline-primary seat-btn rounded-circle" 
                                onclick="markAsSubmitted(${st.id}, ${st.seatNo}, this)">
                            ${st.seatNo}
                        </button>
                    `).join('')}
                </div>
            </div>
        </div>
    `).join('');
}

// 4. 修改單一狀態 [對應：PUT /api/homework-status/{id}]
async function markAsSubmitted(statusId, seatNo, btn) {
    if(!confirm(`確認座號 ${seatNo} 已繳交？`)) return;

    try {
        const response = await fetch(`${API_BASE}/${statusId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ 
                submitted: true,
                submitTime: new Date().toISOString() // 根據文件需求加入時間 
            })
        });

        if (response.ok) {
            btn.style.opacity = '0';
            setTimeout(() => btn.remove(), 300);
        } else {
            throw new Error('更新失敗');
        }
    } catch (error) {
        alert(error.message);
    }
}