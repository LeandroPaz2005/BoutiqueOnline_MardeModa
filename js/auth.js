// js/auth.js

document.addEventListener('DOMContentLoaded', function() {
    const registroForm = document.getElementById('registroForm');
    const loginForm = document.getElementById('loginForm');

    // Función para registrar un usuario
    if (registroForm) {
        registroForm.addEventListener('submit', function(e) {
            e.preventDefault();

            const nombre = document.getElementById('regNombre').value;
            const apellido = document.getElementById('regApellido').value;
            const email = document.getElementById('regEmail').value;
            const password = document.getElementById('regPassword').value;
            const telefono = document.getElementById('regTelefono').value;

            if (!nombre || !apellido || !email || !password) {
                alert('Por favor, completa todos los campos obligatorios: Nombre, Apellido, Correo y Contraseña.');
                return;
            }

            let usuarios = JSON.parse(localStorage.getItem('usuariosRegistrados')) || [];
            const usuarioExistente = usuarios.find(user => user.email === email);
            if (usuarioExistente) {
                alert('Este correo electrónico ya está registrado. Por favor, inicia sesión o usa otro correo.');
                return;
            }

            usuarios.push({ nombre, apellido, email, password, telefono });
            localStorage.setItem('usuariosRegistrados', JSON.stringify(usuarios));

            alert('¡Registro exitoso! Ahora puedes iniciar sesión.');
            registroForm.reset();
            window.location.href = 'login.html';
        });

        // Lógica para mostrar/ocultar contraseña en el formulario de registro
        // **IMPORTANTE: Usa el ID específico para el ojo de registro**
        const toggleRegPassword = document.getElementById('toggleRegPassword'); // <-- ID CORREGIDO AQUÍ
        const regPassword = document.getElementById('regPassword');

        if (toggleRegPassword && regPassword) {
            toggleRegPassword.addEventListener('click', function() {
                const type = regPassword.getAttribute('type') === 'password' ? 'text' : 'password';
                regPassword.setAttribute('type', type);
                this.classList.toggle('fa-eye');
                this.classList.toggle('fa-eye-slash');
            });
        }
    }

    // Función para iniciar sesión
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            console.log('Evento submit del formulario disparado!'); // <-- Añade esto
            e.preventDefault();

            const email = loginForm.querySelector('input[type="email"]').value;
            const password = loginForm.querySelector('input[type="password"]').value;

            let usuarios = JSON.parse(localStorage.getItem('usuariosRegistrados')) || [];
            const usuarioEncontrado = usuarios.find(user => user.email === email && user.password === password);

            if (usuarioEncontrado) {
                localStorage.setItem('usuarioLogeado', JSON.stringify({
                    nombre: usuarioEncontrado.nombre,
                    apellido: usuarioEncontrado.apellido,
                    email: usuarioEncontrado.email
                }));
                alert('¡Inicio de sesión exitoso! Redirigiendo...');
                window.location.href = 'index.html';
            } else {
                alert('Correo o contraseña incorrectos.');
            }
        });

        // Lógica para mostrar/ocultar contraseña en el formulario de login
        // **IMPORTANTE: Usa el ID específico para el ojo de login**
        const toggleLoginPassword = document.getElementById('toggleLoginPassword'); // <-- ID CORREGIDO AQUÍ (debe coincidir con login.html)
        const loginPassword = document.getElementById('loginPassword');

        if (toggleLoginPassword && loginPassword) {
            toggleLoginPassword.addEventListener('click', function() {
                const type = loginPassword.getAttribute('type') === 'password' ? 'text' : 'password';
                loginPassword.setAttribute('type', type);
                this.classList.toggle('fa-eye');
                this.classList.toggle('fa-eye-slash');
            });
        }
    }

    // Lógica para el botón de cerrar sesión (si existe en el DOM)
    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', function(e) {
            e.preventDefault();
            localStorage.removeItem('usuarioLogeado');
            window.location.href = 'index.html';
        });
    }
});