async function loadExams() {
    const res = await App.api.get('/api/exams');
    if (res.success) {
        const tbody = document.querySelector('#exams-table tbody');
        tbody.innerHTML = res.data.map(exam => `
            <tr>
                <td>${exam.id}</td>
                <td>${exam.course?.name || ''}</td>
                <td>${exam.examType}</td>
                <td>${exam.totalMarks}</td>
                <td>${App.ui.formatDate(exam.date)}</td>
                <td>${exam.durationMinutes}m</td>
                <td><span class="badge badge-${exam.status}">${exam.status}</span></td>
                <td>
                    <button class="btn-secondary" onclick="showEditExamModal(${exam.id})">Edit</button>
                    <button class="btn-danger" onclick="handleDeleteExam(${exam.id})">Delete</button>
                </td>
            </tr>
        `).join('');
    }
}

async function showCreateExamModal() {
    const coursesRes = await App.api.get('/api/courses');
    const courses = coursesRes.success ? coursesRes.data : [];
    
    const html = `
        <form id="exam-form">
            <div class="form-group">
                <label>Course</label>
                <select id="exam-courseId" required>
                    ${courses.map(c => `<option value="${c.id}">${c.name}</option>`).join('')}
                </select>
            </div>
            <div class="form-group">
                <label>Exam Type</label>
                <select id="exam-type" required>
                    <option value="MIDTERM">MIDTERM</option>
                    <option value="SEMESTER">SEMESTER</option>
                    <option value="SUPPLEMENTARY">SUPPLEMENTARY</option>
                    <option value="PRACTICAL">PRACTICAL</option>
                    <option value="INTERNAL">INTERNAL</option>
                </select>
            </div>
            <div class="form-group">
                <label>Date</label>
                <input type="date" id="exam-date" required>
            </div>
            <div class="form-group">
                <label>Total Marks</label>
                <input type="number" id="exam-totalMarks" required>
            </div>
        </form>
    `;
    
    App.ui.showModal('Create Exam', html, async () => {
        const data = {
            courseId: document.getElementById('exam-courseId').value,
            examType: document.getElementById('exam-type').value,
            date: document.getElementById('exam-date').value,
            totalMarks: document.getElementById('exam-totalMarks').value,
            passingMarks: 40,
            durationMinutes: 120,
            status: 'SCHEDULED'
        };
        const res = await App.api.post('/api/exams', data);
        if (res.success) {
            App.ui.showToast('Exam created');
            loadExams();
            return true;
        }
        App.ui.showToast(res.message, 'error');
        return false;
    });
}

document.getElementById('btn-create-exam')?.addEventListener('click', showCreateExamModal);

async function handleDeleteExam(id) {
    if (confirm('Are you sure you want to delete this exam?')) {
        const res = await App.api.delete(`/api/exams/${id}`);
        if (res.success) {
            App.ui.showToast('Exam deleted');
            loadExams();
        } else {
            App.ui.showToast(res.message, 'error');
        }
    }
}
window.handleDeleteExam = handleDeleteExam;
