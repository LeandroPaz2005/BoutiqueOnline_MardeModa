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

    // Función genérica para inicializar sliders
    function initializeSlider(sliderId, interval = 5000) {
        const sliderContainer = document.getElementById(sliderId);
        if (!sliderContainer) return;

        const slides = sliderContainer.getElementsByClassName("slide");
        const dotsContainer = sliderContainer.nextElementSibling; // Asumimos que los dots están inmediatamente después
        const dots = dotsContainer ? dotsContainer.getElementsByClassName("dot") : [];
        let slideIndex = 0;
        let timeoutId;

        function showSlide(n) {
            for (let i = 0; i < slides.length; i++) {
                slides[i].style.display = "none";
            }
            for (let i = 0; i < dots.length; i++) {
                dots[i].className = dots[i].className.replace(" active", "");
            }
            slideIndex = (n + slides.length) % slides.length; // Para que sea circular
            slides[slideIndex].style.display = "block";
            if (dots.length > 0) {
                dots[slideIndex].className += " active";
            }
        }

        function nextSlide() {
            showSlide(slideIndex + 1);
            timeoutId = setTimeout(nextSlide, interval);
        }

        function changeSlide(n) {
            clearTimeout(timeoutId);
            showSlide(slideIndex + n);
            timeoutId = setTimeout(nextSlide, interval);
        }

        function currentSlide(n) {
            clearTimeout(timeoutId);
            showSlide(n - 1);
            timeoutId = setTimeout(nextSlide, interval);
        }

        // Inicializar el primer slide y el intervalo
        showSlide(0);
        timeoutId = setTimeout(nextSlide, interval);

        // Asignar las funciones al scope global para los onclick en HTML
        window[`changeSlide`] = (id, n) => {
            if (id === sliderId) {
                changeSlide(n);
            }
        };
        window[`currentSlide`] = (id, n) => {
            if (id === sliderId) {
                currentSlide(n);
            }
        };

        // Generar dots dinámicamente si no existen
        if (dotsContainer && dots.length === 0 && slides.length > 0) {
            for (let i = 0; i < slides.length; i++) {
                const dot = document.createElement('span');
                dot.classList.add('dot');
                dot.onclick = () => window[`currentSlide`](sliderId, i + 1); // Usar la función global
                dotsContainer.appendChild(dot);
            }
            // Actualizar la referencia a los dots
            const newDots = dotsContainer.getElementsByClassName("dot");
            if (newDots.length > 0 && slides.length > 0) {
                newDots[0].classList.add('active');
            }
        }
    }

    // Inicializar los sliders
    initializeSlider('mainSlider', 5000);
    initializeSlider('productosSlider', 3000); // Un intervalo diferente para productos destacados
    initializeSlider('promocionesSlider', 4000);

    // TogglePassword (se mantiene igual)
    const togglePassword = document.getElementById("togglePassword");
    const passwordInput = document.getElementById("password");

    if (togglePassword && passwordInput) {
        togglePassword.addEventListener("click", function () {
            const type = passwordInput.getAttribute("type") === "password" ? "text" : "password";
            passwordInput.setAttribute("type", type);
            this.classList.toggle("fa-eye-slash");
        });
    }

    // Ordenar por (se mantiene igual)
    const sortSelect = document.querySelector('.sort select');
    const productGrid = document.querySelector('.producto-grid');

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

            // Re-append sorted products to the grid
            sortedProducts.forEach(product => productGrid.appendChild(product));
        });
    }
});

// Admin - dropdown
document.addEventListener('DOMContentLoaded', function() {
    const adminDropdown = document.querySelector('.admin-dropdown');
    const adminBtn = document.querySelector('.admin-btn');

    if (adminBtn && adminDropdown) {
        adminBtn.addEventListener('click', function() {
            adminDropdown.classList.toggle('active');
        });

        // Cerrar el dropdown si se hace clic fuera de él
        window.addEventListener('click', function(event) {
            if (!event.target.matches('.admin-btn') && !adminDropdown.contains(event.target)) {
                adminDropdown.classList.remove('active');
            }
        });
    }
});