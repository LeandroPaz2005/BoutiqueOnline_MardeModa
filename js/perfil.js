document.addEventListener('DOMContentLoaded', function() {
    const perfilNombre = document.getElementById('perfilNombre');
    const perfilApellido = document.getElementById('perfilApellido'); // Si quieres mostrar el apellido
    const perfilEmail = document.getElementById('perfilEmail');
    const cerrarSesionPerfilBtn = document.getElementById('cerrarSesionPerfilBtn');

    function loadPerfilInfo() {
        const usuarioLogeado = JSON.parse(localStorage.getItem('usuarioLogeado'));

        if (usuarioLogeado) {
            perfilNombre.textContent = usuarioLogeado.nombre;
            perfilApellido.textContent = usuarioLogeado.apellido; // Asigna el apellido
            perfilEmail.textContent = usuarioLogeado.email;
        } else {
            // Si no hay usuario logeado, redirigir a la página de login
            alert('Debes iniciar sesión para ver tu perfil.');
            window.location.href = 'login.html';
        }
    }

    loadPerfilInfo();

    if (cerrarSesionPerfilBtn) {
        cerrarSesionPerfilBtn.addEventListener('click', function() {
            localStorage.removeItem('usuarioLogeado'); // Eliminar la sesión
            alert('Has cerrado sesión.');
            window.location.href = 'index.html'; // Redirigir al inicio después de cerrar sesión
        });
    }
});