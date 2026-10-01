document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            
            const btn = e.target.querySelector('button');
            const origText = btn.textContent;
            btn.textContent = 'Loading...';
            btn.disabled = true;

            const res = await App.api.post('/api/auth/login', { username, password });
            
            btn.textContent = origText;
            btn.disabled = false;

            if (res.success && res.data) {
                App.auth.setToken(res.data.token);
                App.auth.setUser(res.data);
                App.ui.showToast(`Welcome back, ${res.data.fullName}`);
                
                // Adjust sidebar visibility based on role if needed
                App.router.showDashboard();
            } else {
                App.ui.showToast(res.message || 'Login failed', 'error');
            }
        });
    }
});
