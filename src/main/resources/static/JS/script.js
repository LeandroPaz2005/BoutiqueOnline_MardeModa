document.addEventListener("DOMContentLoaded", function () {
    // Mensajes promocionales
    const mensajes = [
        "¡Envío gratis por compras mayores a S/ 150!",
        "¡Blusas seleccionadas con 30% de descuento solo por hoy!",
        "Regístrate y obtén un cupón de S/ 20 de descuento.",
        "¡Nuevas colecciones disponibles cada semana!",
        "Compra 2 y llévate 1 gratis en productos seleccionados."
    ];
    let mensajeIndice = 0;
    const mensajeElemento = document.getElementById("mensaje-oferta");

    if (mensajeElemento) {
        mensajeElemento.textContent = mensajes[mensajeIndice];
        mensajeIndice++;
        setInterval(() => {
            mensajeElemento.textContent = mensajes[mensajeIndice];
            mensajeIndice = (mensajeIndice + 1) % mensajes.length;
        }, 5000);
    }

    // --- INICIO: Lógica de Autenticación en el Header ---
    // Asegúrate de que estos IDs existen en tu HTML (actualmente no están en el index que me mostraste)
    // Los divs auth-buttons y user-logged-controls ya se manejan con Thymeleaf,
    // pero si tienes lógica JS adicional para ellos, deberías asegurarte que los elementos existan.
    // En tu HTML actual, la visibilidad de los botones de login/registro y el menú de usuario
    // ya la controla Thymeleaf con th:if="${usuario == null}" y th:if="${usuario != null}".
    // Las siguientes líneas de JS solo tendrían efecto si también hay elementos con esos IDs.
    const authLinksContainer = document.getElementById('auth-links-container'); // No existe en tu HTML
    const userIconLink = document.getElementById('userIconLink'); // No existe en tu HTML
    const userNameDisplay = document.getElementById('userNameDisplay'); // No existe en tu HTML (el nombre está dentro de .user-info)
    const loginLink = document.getElementById('loginLink'); // No existe en tu HTML (es un <a> con clase btn)
    const registroLink = document.getElementById('registroLink'); // No existe en tu HTML (es un <a> con clase btn)
    const logoutButton = document.getElementById('logoutButton'); // No existe en tu HTML (el enlace de cerrar sesión está dentro del dropdown)

    function checkUserStatusAndRenderHeader() {
        // Esta función depende de que tengas una forma de almacenar el estado del usuario en el cliente,
        // como localStorage, si no se maneja solo a nivel de servidor con Thymeleaf.
        // Si el estado de ${usuario} solo viene del servidor, esta función de JS no hará nada.
        const usuarioLogeado = JSON.parse(localStorage.getItem('usuarioLogeado'));

        console.log('script.js: checkUserStatusAndRenderHeader se ejecutó.');
        console.log('script.js: Valor de usuarioLogeado en localStorage:', usuarioLogeado);

        // La lógica de mostrar/ocultar los botones de login/registro y el menú de usuario
        // ya se maneja directamente en tu HTML con `th:if` y `th:unless`.
        // Si aún quieres controlarlo con JS basado en localStorage, necesitarías que los elementos
        // tengan IDs únicos para poder seleccionarlos y manipular su `style.display`.
        // Por ahora, tu HTML es el que tiene la última palabra aquí.
    }

    checkUserStatusAndRenderHeader();
    window.addEventListener('storage', checkUserStatusAndRenderHeader); // Escuchar cambios en localStorage

    // Si el botón de logout tiene un ID específico y se maneja con JS para eliminar del localStorage
    // y redirigir, este bloque es relevante. Si no, se puede quitar.
    /*
    if (logoutButton) {
        logoutButton.addEventListener('click', function(e) {
            e.preventDefault();
            localStorage.removeItem('usuarioLogeado');
            checkUserStatusAndRenderHeader();
            window.location.href = 'index.html';
        });
    }
    */
    // Función para sliders reutilizable
    function initializeSlider(sliderId, interval = 5000) {
        const sliderContainer = document.getElementById(sliderId);
        if (!sliderContainer) return;

        const slides = sliderContainer.getElementsByClassName("slide");
        const dotsContainer = sliderContainer.nextElementSibling;
        const dots = dotsContainer ? dotsContainer.getElementsByClassName("dot") : [];

        let slideIndex = 0;
        let timeoutId;

        function showSlide(n) {
            for (let i = 0; i < slides.length; i++) {
                slides[i].style.display = "none";
                slides[i].classList.remove("active-slide");
            }
            for (let i = 0; i < dots.length; i++) {
                dots[i].classList.remove("active");
            }

            slideIndex = (n + slides.length) % slides.length;

            if (slides[slideIndex]) {
                slides[slideIndex].style.display = "block";
                slides[slideIndex].classList.add("active-slide");
            }
            if (dots[slideIndex]) {
                dots[slideIndex].classList.add("active");
            }
        }

        function nextSlide() {
            showSlide(slideIndex + 1);
            timeoutId = setTimeout(nextSlide, interval);
        }

        // Nombres de funciones accesibles globalmente
        const capitalized = sliderId.charAt(0).toUpperCase() + sliderId.slice(1);

        window[`change${capitalized}Slide`] = function(n) {
            clearTimeout(timeoutId);
            showSlide(slideIndex + n);
            timeoutId = setTimeout(nextSlide, interval);
        };

        window[`current${capitalized}Slide`] = function(n) {
            clearTimeout(timeoutId);
            showSlide(n - 1);
            timeoutId = setTimeout(nextSlide, interval);
        };

        showSlide(0);
        timeoutId = setTimeout(nextSlide, interval);
    }

    // Inicializar sliders
    initializeSlider("promocionesSlider", 4000);
    initializeSlider("productosSlider", 3000);


    // TogglePassword
    function setupPasswordToggle(passwordInputId, toggleIconId) {
        const passwordInput = document.getElementById(passwordInputId);
        const togglePasswordIcon = document.getElementById(toggleIconId);

        if (passwordInput && togglePasswordIcon) {
            togglePasswordIcon.addEventListener('click', function() {
                const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
                passwordInput.setAttribute('type', type);
                this.classList.toggle('fa-eye');
                this.classList.toggle('fa-eye-slash');
            });
        }
    }

    // Aplicar la funcionalidad a los diferentes formularios
    // Asegúrate de que los IDs 'password' y 'togglePassword' existan en tus formularios
    setupPasswordToggle('password', 'togglePassword');


    // Ordenar por (se mantiene igual, asumiendo que tienes .sort select y .producto-grid en alguna página)
    const sortSelect = document.querySelector('.sort select');
    const productGrid = document.querySelector('.producto-grid'); // Nota: Tu HTML usa .productos-grid

    if (sortSelect && productGrid) {
        sortSelect.addEventListener('change', () => {
            const products = Array.from(productGrid.getElementsByClassName('producto'));
            let sortedProducts;

            if (sortSelect.value === 'Precio: Bajo a Alto') {
                sortedProducts = products.sort((a, b) => {
                    const priceA = parseFloat(a.querySelector('p').textContent.match(/S\/ (\d+)/)[1]);
                    const priceB = parseFloat(b.querySelector('p').textContent.match(/S\/ (\d+)/)[1]);
                    return priceA - priceB;
                });
            } else if (sortSelect.value === 'Precio: Alto a Bajo') {
                sortedProducts = products.sort((a, b) => {
                    const priceA = parseFloat(a.querySelector('p').textContent.match(/S\/ (\d+)/)[1]);
                    const priceB = parseFloat(b.querySelector('p').textContent.match(/S\/ (\d+)/)[1]);
                    return priceB - priceA;
                });
            }

            if (sortedProducts) { // Asegurarse de que sortedProducts no sea undefined
                 sortedProducts.forEach(product => productGrid.appendChild(product));
            }
        });
    }

    // Admin - dropdown (se mantiene igual)
    const adminDropdown = document.querySelector('.admin-dropdown');
    const adminBtn = document.querySelector('.admin-btn');

    if (adminBtn && adminDropdown) {
        adminBtn.addEventListener('click', function () {
            adminDropdown.classList.toggle('active');
        });

        window.addEventListener('click', function (event) {
            if (!event.target.matches('.admin-btn') && !adminDropdown.contains(event.target)) {
                adminDropdown.classList.remove('active');
            }
        });
    }

    // Mostrar con fade-in cuando entra al viewport
    const observador = new IntersectionObserver((entradas) => {
        entradas.forEach(entrada => {
            if (entrada.isIntersecting) {
                entrada.target.classList.add('visible');
            }
        });
    }, {
        threshold: 0.1
    });

    document.querySelectorAll('.fade-in-section').forEach(seccion => {
        observador.observe(seccion);
    });
});
    document.addEventListener("DOMContentLoaded", function () {
        const menuButton = document.querySelector(".admin-btn");
        const dropdownMenu = document.querySelector(".dropdown-content");

        if (menuButton && dropdownMenu) {
            menuButton.addEventListener("click", function (e) {
                e.stopPropagation(); // evitar que se cierre inmediatamente
                dropdownMenu.classList.toggle("show");
            });

            // Cerrar si se hace clic fuera
            window.addEventListener("click", function () {
                if (dropdownMenu.classList.contains("show")) {
                    dropdownMenu.classList.remove("show");
                }
            });
        }
    });

