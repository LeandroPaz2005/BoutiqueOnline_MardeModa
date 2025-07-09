/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

// validation.js
document.addEventListener('DOMContentLoaded', function() {
    // Obtener referencias a los campos de contraseña y al formulario
    // ¡Asegúrate de que los IDs aquí coincidan con los IDs ÚNICOS que definiste en tu HTML!
    const newPasswordField = document.getElementById('newPasswordInput');
    const confirmNewPasswordField = document.getElementById('confirmNewPasswordInput');
    
    // Asegúrate de que tu formulario HTML tenga un ID, por ejemplo, <form id="changePasswordForm" ...>
    const changePasswordForm = document.getElementById('changePasswordForm');

    // Solo ejecuta la lógica si los elementos necesarios existen en la página
    if (newPasswordField && confirmNewPasswordField && changePasswordForm) {
        changePasswordForm.addEventListener('submit', function(event) {
            // 1. Validación: La nueva contraseña y la confirmación deben coincidir
            if (newPasswordField.value !== confirmNewPasswordField.value) {
                alert('Error: La nueva contraseña y la confirmación no coinciden.');
                event.preventDefault(); // Detener el envío del formulario
                // Mejora UX: Aquí podrías añadir un mensaje de error visible en el HTML,
                // por ejemplo, actualizando un <div> con un mensaje de "Las contraseñas no coinciden".
                // return false; // También puedes usar esto para detener la sumisión
            }

            // 2. Validación: Longitud mínima de la nueva contraseña (ejemplo)
            // Esta validación debe coincidir con la que tienes en tu controlador de Spring Boot.
            /*if (newPasswordField.value.length < 6) {
                alert('Error: La nueva contraseña debe tener al menos 6 caracteres.');
                event.preventDefault(); // Detener el envío del formulario
                // Mejora UX: Mostrar mensaje de error en el HTML
            }*/

            // 3. Puedes añadir más validaciones de complejidad aquí (opcional):
            // - Si contiene al menos una mayúscula
            // - Si contiene al menos un número
            // - Si contiene al menos un carácter especial
            // ...

            // Si todas las validaciones pasan, el formulario se enviará.
        });
    }
});
