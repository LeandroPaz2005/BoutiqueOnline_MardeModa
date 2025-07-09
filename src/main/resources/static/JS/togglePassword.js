/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function() {
    // Obtener referencias al input de contraseña y al ícono de toggle
    const passwordInput = document.getElementById('password'); // Asegúrate de que este ID sea el correcto para el input de contraseña
    const togglePasswordIcon = document.getElementById('togglePassword'); // Asegúrate de que este ID sea el correcto para el ícono

    // Solo si ambos elementos existen en la página
    if (passwordInput && togglePasswordIcon) {
        togglePasswordIcon.addEventListener('click', function() {
            // Alternar el tipo del input entre 'password' y 'text'
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);

            // Cambiar la clase del ícono para alternar entre ojo abierto y ojo tachado
            this.classList.toggle('fa-eye');      // Si tiene fa-eye, lo quita; si no, lo añade
            this.classList.toggle('fa-eye-slash'); // Si tiene fa-eye-slash, lo quita; si no, lo añade
        });
    }
});
