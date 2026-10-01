const App = {
    state: {
        token: localStorage.getItem('token'),
        user: JSON.parse(localStorage.getItem('user') || 'null')
    },
    
    api: {
        async request(endpoint, method = 'GET', data = null) {
            const headers = {
                'Content-Type': 'application/json'
            };
            if (App.state.token) {
                headers['Authorization'] = `Bearer ${App.state.token}`;
            }

            const config = { method, headers };
            if (data) {
                config.body = JSON.stringify(data);
            }

            try {
                const response = await fetch(endpoint, config);
                if (response.status === 401) {
                    App.auth.logout();
                    return { success: false, message: 'Session expired' };
                }
                const result = await response.json();
                return result;
            } catch (error) {
                return { success: false, message: error.message };
            }
        },
        get(endpoint) { return this.request(endpoint, 'GET'); },
        post(endpoint, data) { return this.request(endpoint, 'POST', data); },
        put(endpoint, data) { return this.request(endpoint, 'PUT', data); },
        delete(endpoint) { return this.request(endpoint, 'DELETE'); },
        patch(endpoint, data) { return this.request(endpoint, 'PATCH', data); }
    },

    auth: {
        isLoggedIn() { return !!App.state.token; },
        setToken(token) {
            App.state.token = token;
            localStorage.setItem('token', token);
        },
        setUser(user) {
            App.state.user = user;
            localStorage.setItem('user', JSON.stringify(user));
        },
        logout() {
            App.state.token = null;
            App.state.user = null;
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            App.router.showLogin();
        }
    },

    router: {
        showLogin() {
            document.getElementById('dashboard-view').classList.remove('active');
            document.getElementById('login-view').classList.add('active');
        },
        showDashboard() {
            document.getElementById('login-view').classList.remove('active');
            document.getElementById('dashboard-view').classList.add('active');
            document.getElementById('user-info').textContent = App.state.user?.fullName || '';
            this.navigate('dashboard-section');
        },
        navigate(sectionId) {
            document.querySelectorAll('.content-section').forEach(el => el.classList.remove('active'));
            document.querySelectorAll('.nav-item').forEach(el => el.classList.remove('active'));
            
            const targetSection = document.getElementById(sectionId);
            if (targetSection) targetSection.classList.add('active');
            
            const targetNav = document.querySelector(`[data-target="${sectionId}"]`);
            if (targetNav) {
                targetNav.classList.add('active');
                document.getElementById('current-section-title').textContent = targetNav.textContent;
            }

            // Trigger section specific load
            if (sectionId === 'dashboard-section' && window.loadDashboard) loadDashboard();
            if (sectionId === 'exams-section' && window.loadExams) loadExams();
            if (sectionId === 'schedules-section' && window.loadSchedules) loadSchedules();
            if (sectionId === 'rooms-section' && window.loadRooms) loadRooms();
            if (sectionId === 'users-section' && window.loadUsers) loadUsers();
            if (sectionId === 'results-section' && window.loadResults) loadResults();
            if (sectionId === 'courses-section' && window.loadCourses) window.loadCourses(); // Assuming we might add courses.js
        }
    },

    ui: {
        showToast(message, type = 'success') {
            const container = document.getElementById('toast-container');
            const toast = document.createElement('div');
            toast.className = `toast toast-${type}`;
            toast.textContent = message;
            container.appendChild(toast);
            setTimeout(() => {
                toast.remove();
            }, 3000);
        },
        
        showModal(title, bodyHtml, onSave) {
            document.getElementById('modal-title').textContent = title;
            document.getElementById('modal-body').innerHTML = bodyHtml;
            const modal = document.getElementById('modal-container');
            modal.classList.remove('hidden');
            
            const saveBtn = document.getElementById('modal-save');
            const newSaveBtn = saveBtn.cloneNode(true);
            saveBtn.parentNode.replaceChild(newSaveBtn, saveBtn);
            
            newSaveBtn.addEventListener('click', async () => {
                if (onSave) {
                    const close = await onSave();
                    if (close !== false) this.closeModal();
                } else {
                    this.closeModal();
                }
            });
        },
        
        closeModal() {
            document.getElementById('modal-container').classList.add('hidden');
        },

        formatDate(dateStr) {
            if (!dateStr) return '';
            return new Date(dateStr).toLocaleDateString();
        },
        
        formatTime(timeStr) {
            if (!timeStr) return '';
            if (timeStr.length === 5) return timeStr; // HH:mm
            return timeStr.substring(0, 5);
        }
    }
};

document.addEventListener('DOMContentLoaded', () => {
    if (App.auth.isLoggedIn()) {
        App.router.showDashboard();
    } else {
        App.router.showLogin();
    }

    document.getElementById('logout-btn')?.addEventListener('click', () => {
        App.auth.logout();
    });

    document.querySelectorAll('.nav-item').forEach(item => {
        item.addEventListener('click', (e) => {
            e.preventDefault();
            App.router.navigate(e.target.dataset.target);
        });
    });

    document.getElementById('modal-cancel')?.addEventListener('click', () => {
        App.ui.closeModal();
    });
});
