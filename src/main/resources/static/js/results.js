async function loadResults() {
    const res = await App.api.get('/api/results');
    if (res.success) {
        const tbody = document.querySelector('#results-table tbody');
        tbody.innerHTML = res.data.map(r => `
            <tr>
                <td>${r.id}</td>
                <td>Exam ${r.exam?.id || ''}</td>
                <td>${r.student?.fullName || ''}</td>
                <td>${r.marksObtained}</td>
                <td>${r.grade}</td>
                <td>${r.remarks || ''}</td>
                <td>${App.ui.formatDate(r.publishedAt)}</td>
                <td>
                    <button class="btn-danger" onclick="handleDeleteResult(${r.id})">Delete</button>
                </td>
            </tr>
        `).join('');
    }
}

async function handleDeleteResult(id) {
    if (confirm('Delete this result?')) {
        const res = await App.api.delete(`/api/results/${id}`);
        if (res.success) {
            App.ui.showToast('Result deleted');
            loadResults();
        }
    }
}
window.handleDeleteResult = handleDeleteResult;

document.getElementById('btn-create-result')?.addEventListener('click', async () => {
    App.ui.showModal('Add Result', '<p>Loading...</p>', null);
    
    const [exams, students] = await Promise.all([
        App.api.get('/api/exams'),
        App.api.get('/api/users/role/STUDENT')
    ]);

    const html = `
        <form id="result-form">
            <div class="form-group">
                <label>Exam</label>
                <select id="res-examId" required>
                    ${(exams.data||[]).map(e => `<option value="${e.id}">Exam ${e.id}</option>`).join('')}
                </select>
            </div>
            <div class="form-group">
                <label>Student</label>
                <select id="res-studentId" required>
                    ${(students.data||[]).map(s => `<option value="${s.id}">${s.fullName}</option>`).join('')}
                </select>
            </div>
            <div class="form-group">
                <label>Marks Obtained</label>
                <input type="number" step="0.5" id="res-marks" required>
            </div>
            <div class="form-group">
                <label>Grade</label>
                <input type="text" id="res-grade" required>
            </div>
            <div class="form-group">
                <label>Remarks</label>
                <input type="text" id="res-remarks">
            </div>
        </form>
    `;
    
    App.ui.showModal('Add Result', html, async () => {
        const data = {
            examId: document.getElementById('res-examId').value,
            studentId: document.getElementById('res-studentId').value,
            marksObtained: parseFloat(document.getElementById('res-marks').value),
            grade: document.getElementById('res-grade').value,
            remarks: document.getElementById('res-remarks').value
        };
        const res = await App.api.post('/api/results', data);
        if (res.success) {
            App.ui.showToast('Result added');
            loadResults();
            return true;
        }
        App.ui.showToast(res.message, 'error');
        return false;
    });
});
