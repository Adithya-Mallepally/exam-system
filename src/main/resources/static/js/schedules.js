async function loadSchedules() {
    const res = await App.api.get('/api/schedules');
    if (res.success) {
        const tbody = document.querySelector('#schedules-table tbody');
        tbody.innerHTML = res.data.map(sch => `
            <tr>
                <td>Exam ${sch.exam?.id || ''}</td>
                <td>${sch.room?.name || ''}</td>
                <td>${sch.invigilator?.fullName || ''}</td>
                <td>${App.ui.formatDate(sch.date)}</td>
                <td>${App.ui.formatTime(sch.startTime)} - ${App.ui.formatTime(sch.endTime)}</td>
                <td>${sch.seatCount}</td>
                <td>
                    <button class="btn-danger" onclick="handleDeleteSchedule(${sch.id})">Delete</button>
                </td>
            </tr>
        `).join('');
    }
}

async function showCreateScheduleModal() {
    App.ui.showModal('Create Schedule', '<p>Loading options...</p>', null);
    
    const [exams, rooms, faculty] = await Promise.all([
        App.api.get('/api/exams'),
        App.api.get('/api/rooms/available'),
        App.api.get('/api/users/role/FACULTY')
    ]);

    const html = `
        <form id="schedule-form">
            <div class="form-group">
                <label>Exam</label>
                <select id="sch-examId" required>
                    ${(exams.data||[]).map(e => `<option value="${e.id}">${e.course?.name} - ${e.examType}</option>`).join('')}
                </select>
            </div>
            <div class="form-group">
                <label>Room</label>
                <select id="sch-roomId" required>
                    ${(rooms.data||[]).map(r => `<option value="${r.id}">${r.name} (Cap: ${r.capacity})</option>`).join('')}
                </select>
            </div>
            <div class="form-group">
                <label>Invigilator</label>
                <select id="sch-invigilatorId" required>
                    ${(faculty.data||[]).map(f => `<option value="${f.id}">${f.fullName}</option>`).join('')}
                </select>
            </div>
            <div class="form-group">
                <label>Date</label>
                <input type="date" id="sch-date" required>
            </div>
        </form>
    `;
    
    App.ui.showModal('Create Schedule', html, async () => {
        const data = {
            examId: document.getElementById('sch-examId').value,
            roomId: document.getElementById('sch-roomId').value,
            invigilatorId: document.getElementById('sch-invigilatorId').value,
            date: document.getElementById('sch-date').value,
            startTime: "10:00",
            endTime: "13:00",
            seatCount: 30
        };
        const res = await App.api.post('/api/schedules', data);
        if (res.success) {
            App.ui.showToast('Schedule created');
            loadSchedules();
            return true;
        }
        App.ui.showToast(res.message, 'error');
        return false;
    });
}

document.getElementById('btn-create-schedule')?.addEventListener('click', showCreateScheduleModal);
document.getElementById('btn-download-schedule')?.addEventListener('click', async () => {
    try {
        const response = await fetch('/api/reports/exam-schedule/csv', {
            headers: { 'Authorization': `Bearer ${App.state.token}` }
        });
        if (!response.ok) throw new Error('Download failed');
        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'exam-schedule-report.csv';
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
    } catch (e) {
        App.ui.showToast('Report download failed', 'error');
    }
});

async function handleDeleteSchedule(id) {
    if (confirm('Delete this schedule?')) {
        const res = await App.api.delete(`/api/schedules/${id}`);
        if (res.success) {
            App.ui.showToast('Schedule deleted');
            loadSchedules();
        }
    }
}
window.handleDeleteSchedule = handleDeleteSchedule;
