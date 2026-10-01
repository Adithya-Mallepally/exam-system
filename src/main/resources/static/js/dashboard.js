let dashboardRefreshTimer = null;

async function loadDashboard() {
    clearTimeout(dashboardRefreshTimer);
    if (!App.auth.isLoggedIn()) return;

    const statsRes = await App.api.get('/api/dashboard/stats');
    if (statsRes.success && statsRes.data) {
        const stats = statsRes.data;
        const grid = document.getElementById('dashboard-stats');
        grid.innerHTML = `
            <div class="stat-card">
                <h3>Total Students</h3>
                <div class="value">${stats.totalStudents || 0}</div>
            </div>
            <div class="stat-card">
                <h3>Total Faculty</h3>
                <div class="value">${stats.totalFaculty || 0}</div>
            </div>
            <div class="stat-card">
                <h3>Total Exams</h3>
                <div class="value">${stats.totalExams || 0}</div>
                <div style="font-size: 0.8rem; color: var(--text-muted)">${stats.upcomingExams || 0} upcoming</div>
            </div>
            <div class="stat-card">
                <h3>Available Rooms</h3>
                <div class="value">${stats.availableRooms || 0} / ${stats.totalRooms || 0}</div>
            </div>
            <div class="stat-card">
                <h3>Completed Exams</h3>
                <div class="value">${stats.completedExams || 0}</div>
            </div>
        `;
    }

    const activityRes = await App.api.get('/api/dashboard/recent-activity');
    if (activityRes.success && activityRes.data) {
        const tbody = document.querySelector('#recent-activity-table tbody');
        tbody.innerHTML = activityRes.data.map(exam => `
            <tr>
                <td>${exam.instructions || 'Exam ' + exam.id}</td>
                <td>${exam.course?.name || 'N/A'}</td>
                <td>${exam.examType || 'N/A'}</td>
                <td>${App.ui.formatDate(exam.date)}</td>
                <td><span class="badge badge-${exam.status}">${exam.status}</span></td>
            </tr>
        `).join('');
    }

    dashboardRefreshTimer = setTimeout(loadDashboard, 30000);
}
