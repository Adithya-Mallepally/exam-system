async function loadCourses() {
    const res = await App.api.get('/api/courses');
    if (res.success) {
        const tbody = document.querySelector('#courses-table tbody');
        tbody.innerHTML = res.data.map(c => `
            <tr>
                <td>${c.id}</td>
                <td>${c.code}</td>
                <td>${c.name}</td>
                <td>${c.department}</td>
                <td>${c.semester}</td>
                <td>${c.creditHours}</td>
                <td>
                    <button class="btn-danger" onclick="handleDeleteCourse(${c.id})">Delete</button>
                </td>
            </tr>
        `).join('');
    }
}

async function handleDeleteCourse(id) {
    if (confirm('Delete this course?')) {
        const res = await App.api.delete(`/api/courses/${id}`);
        if (res.success) {
            App.ui.showToast('Course deleted');
            loadCourses();
        }
    }
}
window.handleDeleteCourse = handleDeleteCourse;
window.loadCourses = loadCourses;

document.getElementById('btn-create-course')?.addEventListener('click', async () => {
    App.ui.showModal('Add Course', '<p>Loading...</p>', null);
    const faculty = await App.api.get('/api/users/role/FACULTY');

    const html = `
        <form id="course-form">
            <div class="form-group">
                <label>Code</label>
                <input type="text" id="crs-code" required>
            </div>
            <div class="form-group">
                <label>Name</label>
                <input type="text" id="crs-name" required>
            </div>
            <div class="form-group">
                <label>Department</label>
                <input type="text" id="crs-department" required>
            </div>
            <div class="form-group">
                <label>Semester</label>
                <input type="number" id="crs-semester" required>
            </div>
            <div class="form-group">
                <label>Credit Hours</label>
                <input type="number" id="crs-credits" required>
            </div>
            <div class="form-group">
                <label>Faculty</label>
                <select id="crs-facultyId">
                    <option value="">None</option>
                    ${(faculty.data||[]).map(f => `<option value="${f.id}">${f.fullName}</option>`).join('')}
                </select>
            </div>
        </form>
    `;
    
    App.ui.showModal('Add Course', html, async () => {
        const data = {
            code: document.getElementById('crs-code').value,
            name: document.getElementById('crs-name').value,
            department: document.getElementById('crs-department').value,
            semester: parseInt(document.getElementById('crs-semester').value),
            creditHours: parseInt(document.getElementById('crs-credits').value),
            facultyId: document.getElementById('crs-facultyId').value || null
        };
        const res = await App.api.post('/api/courses', data);
        if (res.success) {
            App.ui.showToast('Course added');
            loadCourses();
            return true;
        }
        App.ui.showToast(res.message, 'error');
        return false;
    });
});
