async function loadRooms() {
    const res = await App.api.get('/api/rooms');
    if (res.success) {
        const tbody = document.querySelector('#rooms-table tbody');
        tbody.innerHTML = res.data.map(room => `
            <tr>
                <td>${room.id}</td>
                <td>${room.name}</td>
                <td>${room.building}</td>
                <td>${room.floor}</td>
                <td>${room.capacity}</td>
                <td>${room.hasProjector ? 'Yes' : 'No'}</td>
                <td><span class="badge ${room.available ? 'badge-green' : 'badge-red'}">${room.available ? 'Available' : 'Unavailable'}</span></td>
                <td>
                    <button class="btn-danger" onclick="handleDeleteRoom(${room.id})">Delete</button>
                </td>
            </tr>
        `).join('');
    }
}

async function handleDeleteRoom(id) {
    if (confirm('Delete this room?')) {
        const res = await App.api.delete(`/api/rooms/${id}`);
        if (res.success) {
            App.ui.showToast('Room deleted');
            loadRooms();
        }
    }
}
window.handleDeleteRoom = handleDeleteRoom;

document.getElementById('btn-create-room')?.addEventListener('click', () => {
    const html = `
        <form id="room-form">
            <div class="form-group">
                <label>Room Name</label>
                <input type="text" id="rm-name" required>
            </div>
            <div class="form-group">
                <label>Building</label>
                <input type="text" id="rm-building" required>
            </div>
            <div class="form-group">
                <label>Floor</label>
                <input type="number" id="rm-floor" required>
            </div>
            <div class="form-group">
                <label>Capacity</label>
                <input type="number" id="rm-capacity" required>
            </div>
        </form>
    `;
    
    App.ui.showModal('Add Room', html, async () => {
        const data = {
            name: document.getElementById('rm-name').value,
            building: document.getElementById('rm-building').value,
            floor: parseInt(document.getElementById('rm-floor').value),
            capacity: parseInt(document.getElementById('rm-capacity').value),
            hasProjector: false,
            available: true
        };
        const res = await App.api.post('/api/rooms', data);
        if (res.success) {
            App.ui.showToast('Room added');
            loadRooms();
            return true;
        }
        App.ui.showToast(res.message, 'error');
        return false;
    });
});
