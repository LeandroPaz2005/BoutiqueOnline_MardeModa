/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

// passwordToggle.js
document.addEventListener('DOMContentLoaded', function() {
    const togglePasswordIcons = document.querySelectorAll('.password-container .fas.fa-eye');

    togglePasswordIcons.forEach(icon => {
        icon.addEventListener('click', function() {
            const passwordInput = this.previousElementSibling; // Obtiene el input hermano anterior

            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);

            this.classList.toggle('fa-eye');
            this.classList.toggle('fa-eye-slash');
        });
    });
});
