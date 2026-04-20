/**
 *  作業輸入
 */

document.addEventListener('DOMContentLoaded', function() {

	const numberGrid = document.getElementById('numberGrid');
	const totalNumbers = 40;

	// 載入科目
	function loadSubjects() {
		fetch('/api/subjects')
			.then(res => res.json())
			.then(data => {
				const subjectSelect = document.getElementById('subject');

				// 清空舊資料
				subjectSelect.innerHTML = '<option selected disabled>請選擇科目</option>';

				data.forEach(subject => {
					const option = document.createElement('option');
					option.value = subject.id;
					option.textContent = subject.name;
					subjectSelect.appendChild(option);
				});
			})
			.catch(err => console.error('載入科目失敗', err));
	}

	// 根據科目，載入類別
	function loadCategories(subjectId) {
		fetch(`/api/categories/subject/${subjectId}`)
			.then(res => res.json())
			.then(data => {

				const categorySelect = document.getElementById('category');

				categorySelect.innerHTML = '<option selected disabled>請選擇類別</option>';

				data.forEach(category => {
					const option = document.createElement('option');
					option.value = category.id;
					option.textContent = category.name;
					categorySelect.appendChild(option);
				});
			})
			.catch(err => console.error('載入類別失敗', err));
	}

	document.getElementById('subject').addEventListener('change', function() {
		const subjectId = this.value;

		// 清空舊類別
		document.getElementById('category').innerHTML =
			'<option selected disabled>載入中...</option>';

		loadCategories(subjectId);
	});

	// 動態生成數字 1 ~ 40
	for (let i = 1; i <= totalNumbers; i++) {
		const col = document.createElement('div');
		col.className = 'col';

		const box = document.createElement('div');
		box.className = 'num-box rounded';
		box.innerText = i;

		// 點擊切換選取狀態 (多選邏輯)
		box.onclick = function() {
			this.classList.toggle('active');
		};

		col.appendChild(box);
		numberGrid.appendChild(col);
	}

	// 「更正」按鈕邏輯：重設所有選取與輸入
	document.getElementById('btnReset').onclick = function() {
		if (confirm('確定要清除所有輸入嗎？')) {
			// 清除選取框
			document.querySelectorAll('.num-box').forEach(box => {
				box.classList.remove('active');
			});
			// 清除輸入欄位
			document.getElementById('name').value = '';
			document.getElementById('pageNumber').value = '';
			document.getElementById('date').value = '';
			document.getElementById('subject').selectedIndex = 0;
			document.getElementById('category').selectedIndex = 0;
		}
	};

	// 「送出」按鈕邏輯
	document.getElementById('btnSubmit').onclick = function() {

		const selectedNums = Array.from(document.querySelectorAll('.num-box.active'))
			.map(box => parseInt(box.innerText));

		// 取得選單的原始值
		const subjectIdRaw = document.getElementById('subject').value;
		const categoryIdRaw = document.getElementById('category').value;

		const data = {
			subjectId: subjectIdRaw,
			categoryId: categoryIdRaw,
			item: document.getElementById('name').value,
			pageRange: document.getElementById('pageNumber').value,
			assignDate: document.getElementById('date').value,
			unsubmittedSeats: selectedNums
		};

		const jsonData = JSON.stringify(data, null, 2);

		console.log("送出 JSON：");
		console.log(jsonData);

		//  送到後端
		fetch("http://localhost:8081/api/assignments", {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: jsonData
		})
			.then(res => res.json())
			.then(result => {
				console.log("後端回傳：", result);
				alert("作業建立成功！");
			})
			.catch(err => console.error(err));
	};

	loadSubjects();

});