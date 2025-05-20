// Función para obtener el token JWT del localStorage
function getToken() {
    return localStorage.getItem('jwtToken');
}

// Función para verificar si el usuario está autenticado
function isAuthenticated() {
    const token = getToken();
    return token !== null && token !== '';
}

// Función para obtener el rol del usuario a partir del token
async function getUserRole() {
    const token = getToken();
    if (!token) {
        return null;
    }

    try {
        const response = await fetch('/auth/validate', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            // Si el token es inválido, limpiamos el localStorage
            localStorage.removeItem('jwtToken');
            return null;
        }

        const data = await response.json();
        return data.role;
    } catch (error) {
        console.error('Error al validar el token:', error);
        return null;
    }
}

// Función para redireccionar según el rol
async function redirectToDashboard() {
    if (!isAuthenticated()) {
        window.location.href = '/login';
        return;
    }

    const role = await getUserRole();
    
    if (!role) {
        window.location.href = '/login';
        return;
    }    // Redireccionar según el rol
    switch (role) {
        case 'ADMIN':
            window.location.href = '/cliente/all';  // Dashboard de administrador
            break;
        case 'VET':
            window.location.href = '/mascota/edit';  // Dashboard de veterinario
            break;
        case 'CLIENT':
            // Obtener el username (correo) del usuario del localStorage
            const username = localStorage.getItem('username');
            window.location.href = `/cliente-view/perfil?correo=${username}`;  // Dashboard de cliente
            break;
        default:
            window.location.href = '/';
            break;
    }
}

// Función para hacer logout
function logout() {
    // Llamada al endpoint de logout
    fetch('/auth/logout', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${getToken()}`
        }
    }).then(() => {
        // Limpiar el localStorage
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('username');
        
        // Actualizar la UI
        updateAuthUI();
        
        // Redireccionar a la página principal
        window.location.href = '/';
    }).catch(error => {
        console.error('Error al cerrar sesión:', error);
    });
}

// Función para actualizar la UI basado en el estado de autenticación
function updateAuthUI() {
    const isLoggedIn = isAuthenticated();
    
    // Elementos del menú
    const loginBtn = document.getElementById('loginBtn');
    const dashboardBtn = document.getElementById('dashboardBtn');
    const logoutBtn = document.getElementById('logoutBtn');
    
    if (loginBtn && dashboardBtn && logoutBtn) {
        if (isLoggedIn) {
            loginBtn.style.display = 'none';
            dashboardBtn.style.display = 'block';
            logoutBtn.style.display = 'block';
        } else {
            loginBtn.style.display = 'block';
            dashboardBtn.style.display = 'none';
            logoutBtn.style.display = 'none';
        }
    }
    
    // Botones de dashboard en la página
    const dashboardButtons = document.querySelectorAll('.dashboard-btn');
    dashboardButtons.forEach(button => {
        if (button.id !== 'dashboardBtn') { // No modificar el botón del menú que ya manejamos arriba
            if (isLoggedIn) {
                button.textContent = 'Mi Dashboard';
                button.href = '#';
            } else {
                button.textContent = 'Ingresa al portal';
                button.href = '/login';
            }
        }
    });
}

// Detectar si hay un JWT al cargar la página
document.addEventListener('DOMContentLoaded', function() {
    // Actualizar la UI basado en el estado de autenticación
    updateAuthUI();
    
    // Configurar eventos para botones de dashboard
    const dashboardButtons = document.querySelectorAll('.dashboard-btn');
    dashboardButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            if (isAuthenticated()) {
                redirectToDashboard();
            } else {
                window.location.href = '/login';
            }
        });
    });
    
    // Configurar evento para botón de logout
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function(event) {
            event.preventDefault();
            logout();
        });
    }
    
    // Configurar evento para botón de login
    const loginBtn = document.getElementById('loginBtn');
    if (loginBtn) {
        loginBtn.addEventListener('click', function(event) {
            event.preventDefault();
            window.location.href = '/login';
        });
    }
});
