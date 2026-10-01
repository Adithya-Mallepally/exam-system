async function loadUsers() {
    const res = await App.api.get('/api/users');
    if (res.success) {
        const tbody = document.querySelector('#users-table tbody');
        tbody.innerHTML = res.data.map(user => `
            <tr>
                <td>${user.id}</td>
                <td>${user.fullName}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td><span class="badge badge-${user.role}">${user.role}</span></td>
                <td>${user.department || ''}</td>
                <td><span class="badge ${user.active ? 'badge-green' : 'badge-red'}">${user.active ? 'Active' : 'Inactive'}</span></td>
                <td>
                    <button class="btn-secondary" onclick="handleToggleStatus(${user.id})">Toggle Status</button>
                    <button class="btn-danger" onclick="handleDeleteUser(${user.id})">Delete</button>
                </td>
            </tr>
        `).join('');
    }
}

async function handleToggleStatus(id) {
    const res = await App.api.patch(`/api/users/${id}/toggle-status`);
    if (res.success) {
        App.ui.showToast('User status updated');
        loadUsers();
    }
}

async function handleDeleteUser(id) {
    if (confirm('Delete this user?')) {
        const res = await App.api.delete(`/api/users/${id}`);
        if (res.success) {
            App.ui.showToast('User deleted');
            loadUsers();
        }
    }
}
window.handleToggleStatus = handleToggleStatus;
window.handleDeleteUser = handleDeleteUser;

document.getElementById('btn-create-user')?.addEventListener('click', () => {
    const html = `
        <form id="user-form">
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" id="usr-fullName" required>
            </div>
            <div class="form-group">
                <label>Username</label>
                <input type="text" id="usr-username" required>
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="email" id="usr-email" required>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" id="usr-password" required>
            </div>
            <div class="form-group">
                <label>Role</label>
                <select id="usr-role" required>
                    <option value="STUDENT">STUDENT</option>
                    <option value="FACULTY">FACULTY</option>
                    <option value="ADMIN">ADMIN</option>
                </select>
            </div>
        </form>
    `;
    
    App.ui.showModal('Add User', html, async () => {
        const data = {
            fullName: document.getElementById('usr-fullName').value,
            username: document.getElementById('usr-username').value,
            email: document.getElementById('usr-email').value,
            password: document.getElementById('usr-password').value,
            role: document.getElementById('usr-role').value,
            department: 'CS',
            phoneNumber: '0000000000'
        };
        const res = await App.api.post('/api/auth/register', data);
        if (res.success) {
            App.ui.showToast('User added');
            loadUsers();
            return true;
        }
        App.ui.showToast(res.message || 'Error adding user', 'error');
        return false;
    });
});
