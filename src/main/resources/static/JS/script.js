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

    // --- INICIO: Lógica de Autenticación en el Header (NUEVO) ---
    const authLinksContainer = document.getElementById('auth-links-container');
    const userIconLink = document.getElementById('userIconLink');
    const userNameDisplay = document.getElementById('userNameDisplay');
    const loginLink = document.getElementById('loginLink');
    const registroLink = document.getElementById('registroLink');
    const logoutButton = document.getElementById('logoutButton'); // Este botón también puede estar en auth.js, pero lo manejamos aquí para actualizar la UI del header

    function checkUserStatusAndRenderHeader() {
        const usuarioLogeado = JSON.parse(localStorage.getItem('usuarioLogeado'));

        // Agregamos console.log para depurar (mantenlos por ahora)
        console.log('script.js: checkUserStatusAndRenderHeader se ejecutó.');
        console.log('script.js: Valor de usuarioLogeado en localStorage:', usuarioLogeado);

        if (usuarioLogeado) {
            console.log('script.js: Usuario logeado. Nombre:', usuarioLogeado.nombre);
            // Usuario logeado: mostrar icono de usuario y botón de cerrar sesión
            if (userIconLink) { // <--- Añadir este IF
                userIconLink.style.display = 'inline-block';
                userIconLink.href = 'perfil.html'; // Solo si userIconLink existe
                console.log('script.js: userIconLink mostrado.');
            }
            if (userNameDisplay) { // <--- Añadir este IF
                userNameDisplay.textContent = usuarioLogeado.nombre;
                console.log('script.js: userNameDisplay actualizado.');
            }
            if (logoutButton) { // <--- Añadir este IF
                logoutButton.style.display = 'inline-block';
                console.log('script.js: logoutButton mostrado.');
            }

            // Ocultar enlaces de login/registro
            if (loginLink) {
                loginLink.style.display = 'none';
                console.log('script.js: loginLink ocultado.');
            }
            if (registroLink) {
                registroLink.style.display = 'none';
                console.log('script.js: registroLink ocultado.');
            }
        } else {
            console.log('script.js: No hay usuario logeado.');
            // No hay usuario logeado: mostrar botones de login/registro
            if (userIconLink) { // <--- Añadir este IF
                userIconLink.style.display = 'none';
                console.log('script.js: userIconLink ocultado.');
            }
            if (userNameDisplay) { // <--- Añadir este IF
                userNameDisplay.textContent = '';
                console.log('script.js: userNameDisplay limpiado.');
            }
            if (logoutButton) { // <--- Añadir este IF
                logoutButton.style.display = 'none';
                console.log('script.js: logoutButton ocultado.');
            }

            // Mostrar enlaces de login/registro
            if (loginLink) {
                loginLink.style.display = 'inline-block';
                console.log('script.js: loginLink mostrado.');
            }
            if (registroLink) {
                registroLink.style.display = 'inline-block';
                console.log('script.js: registroLink mostrado.');
            }
        }
    }

    // Llamar a esta función al cargar la página para inicializar el estado del header
    checkUserStatusAndRenderHeader();

    // Re-evaluar el estado del usuario cada vez que la página gane foco (por si se logeó/deslogeó en otra pestaña)
    window.addEventListener('storage', checkUserStatusAndRenderHeader);

    // Si el botón de logout está en el header y quieres que actualice el header de inmediato:
    if (logoutButton) {
        logoutButton.addEventListener('click', function(e) {
            e.preventDefault();
            localStorage.removeItem('usuarioLogeado'); // Eliminar la sesión
            checkUserStatusAndRenderHeader(); // Actualizar la interfaz del header
            window.location.href = 'index.html'; // Redirigir al index o a la página de login
        });
    }
    // --- FIN: Lógica de Autenticación en el Header ---

    // Función genérica para inicializar sliders (se mantiene igual)
    function initializeSlider(sliderId, interval = 5000) {
        const sliderContainer = document.getElementById(sliderId);
        if (!sliderContainer) return;

        const slides = sliderContainer.getElementsByClassName("slide");
        const dotsContainer = sliderContainer.nextElementSibling;
        const dots = dotsContainer ? dotsContainer.previousElementSibling.getElementsByClassName("dot") : []; // Ajuste para encontrar los dots
        let slideIndex = 0;
        let timeoutId;

        function showSlide(n) {
            for (let i = 0; i < slides.length; i++) {
                slides[i].style.display = "none";
            }
            for (let i = 0; i < dots.length; i++) {
                dots[i].className = dots[i].className.replace(" active", "");
            }
            slideIndex = (n + slides.length) % slides.length;
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

        showSlide(0);
        timeoutId = setTimeout(nextSlide, interval);

        // Exportar funciones globalmente si se necesitan en botones fuera de este script
        // Esto es útil si tienes botones de flecha o dots fuera de este DOMContentLoaded
        if (sliderId === 'mainSlider') {
            window.changeMainSlide = changeSlide;
            window.currentMainSlide = currentSlide;
        } else if (sliderId === 'productosSlider') {
            window.changeProductosSlide = changeSlide;
            window.currentProductosSlide = currentSlide;
        } else if (sliderId === 'promocionesSlider') {
            window.changePromocionesSlide = changeSlide;
            window.currentPromocionesSlide = currentSlide;
        }


        if (dotsContainer && dots.length === 0 && slides.length > 0) {
            for (let i = 0; i < slides.length; i++) {
                const dot = document.createElement('span');
                dot.classList.add('dot');
                dot.onclick = () => {
                    if (sliderId === 'mainSlider') window.currentMainSlide(i + 1);
                    else if (sliderId === 'productosSlider') window.currentProductosSlide(i + 1);
                    else if (sliderId === 'promocionesSlider') window.currentPromocionesSlide(i + 1);
                };
                dotsContainer.appendChild(dot);
            }
            const newDots = dotsContainer.getElementsByClassName("dot");
            if (newDots.length > 0 && slides.length > 0) {
                newDots[0].classList.add('active');
            }
        }
    }

    initializeSlider('mainSlider', 5000);
    initializeSlider('productosSlider', 3000);
    initializeSlider('promocionesSlider', 4000);

    // TogglePassword
    // Asegúrate de que los IDs de los inputs sean correctos en login.html y signup.html
    const togglePasswordLogin = document.getElementById("togglePassword"); // En login.html y signup.html este es el mismo ID
    const passwordInputLogin = document.getElementById("loginPassword"); // En login.html
    const passwordInputReg = document.getElementById("regPassword"); // En signup.html

    if (togglePasswordLogin && passwordInputLogin) {
        togglePasswordLogin.addEventListener("click", function () {
            const type = passwordInputLogin.getAttribute("type") === "password" ? "text" : "password";
            passwordInputLogin.setAttribute("type", type);
            this.classList.toggle("fa-eye-slash");
        });
    }

    // Para el formulario de registro, si tiene un togglePassword distinto
    const togglePasswordReg = document.getElementById("togglePasswordReg"); // Si usas un ID diferente en registro
    if (togglePasswordReg && passwordInputReg) {
        togglePasswordReg.addEventListener("click", function () {
            const type = passwordInputReg.getAttribute("type") === "password" ? "text" : "password";
            passwordInputReg.setAttribute("type", type);
            this.classList.toggle("fa-eye-slash");
        });
    } else if (togglePasswordLogin && passwordInputReg && !passwordInputLogin) { // Si solo hay un togglePassword y estás en la página de registro
        togglePasswordLogin.addEventListener("click", function () {
            const type = passwordInputReg.getAttribute("type") === "password" ? "text" : "password";
            passwordInputReg.setAttribute("type", type);
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

            sortedProducts.forEach(product => productGrid.appendChild(product));
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

    // Carrito desplegable
    const carritoIconLink = document.querySelector('.header-cart-button');
    const carritoLateral = document.createElement('div');
    carritoLateral.id = 'carrito-lateral';
    document.body.appendChild(carritoLateral);

    const carritoItemsContainerLateral = document.createElement('div');
    carritoItemsContainerLateral.id = 'carrito-items-lateral';
    carritoLateral.appendChild(carritoItemsContainerLateral);

    const totalPrecioLateralContainer = document.createElement('div');
    totalPrecioLateralContainer.classList.add('total-lateral');
    totalPrecioLateralContainer.innerHTML = 'Total: <span id="total-precio-lateral">S/ 0.00</span>';
    carritoLateral.appendChild(totalPrecioLateralContainer);

    const comprarButtonLateral = document.createElement('button');
    comprarButtonLateral.textContent = 'Comprar';
    comprarButtonLateral.classList.add('comprar-btn-lateral');
    comprarButtonLateral.addEventListener('click', () => {
        window.location.href = 'carrito.html';
    });
    carritoLateral.appendChild(comprarButtonLateral);

    const vaciarButtonLateral = document.createElement('button');
    vaciarButtonLateral.textContent = 'Vaciar Carrito';
    vaciarButtonLateral.classList.add('vaciar-btn-lateral');

    vaciarButtonLateral.addEventListener('click', () => {
        localStorage.removeItem('carrito');
        localStorage.removeItem('carritoCompraActualizada');

        if (typeof vaciarCarritoCompleto === 'function') {
            vaciarCarritoCompleto();
        } else {
            actualizarContadorCarrito();
            mostrarCarritoLateral();
        }
    });
    carritoLateral.appendChild(vaciarButtonLateral);

    // Hacemos que estas funciones sean globales para que auth.js y otros scripts puedan llamarlas si es necesario.
    // Esto es importante porque eliminarDelCarritoLateral y agregarAlCarrito pueden ser llamadas desde otros scripts.
    window.actualizarContadorCarrito = function() {
        let carrito = localStorage.getItem('carrito');
        carrito = carrito ? JSON.parse(carrito) : [];
        const contador = carrito.reduce((acc, item) => acc + (item.cantidad || 1), 0); // Contar por cantidad, no solo por item

        const contadorElementHeader = carritoIconLink.querySelector('#carrito-count-header');
        if (contadorElementHeader) {
            contadorElementHeader.textContent = `(${contador})`;
        }

        const botonesCarrito = document.querySelectorAll('.producto .cart-btn');
        botonesCarrito.forEach(boton => {
            let contadorSpan = boton.querySelector('.carrito-contador');
            // La lógica de mostrar/ocultar el contador en los botones de producto es más compleja
            // si solo quieres mostrar el contador del producto específico en el botón.
            // Si es un contador global del carrito en cada botón, el código está bien.
            // Para fines de simplicidad, lo mantendremos como un contador global para el botón
            // PERO ten en cuenta que un contador global por cada botón "añadir" no es lo más común.
            // Normalmente, es solo el icono del header el que muestra el total.
            // Si quieres un contador por producto, la lógica debe ser mucho más específica.
            if (contador > 0) {
                 if (!contadorSpan) {
                     contadorSpan = document.createElement('span');
                     contadorSpan.classList.add('carrito-contador');
                     boton.appendChild(contadorSpan);
                 }
                 contadorSpan.textContent = contador;
             } else if (contadorSpan) {
                 contadorSpan.remove();
             }
        });
    }

    window.agregarAlCarrito = function(producto) {
        let carrito = localStorage.getItem('carrito');
        carrito = carrito ? JSON.parse(carrito) : [];

        const itemId = producto.nombre.replace(/\s+/g, '-').toLowerCase() + '-' + (producto.talla ? producto.talla.replace(/\s+/g, '-').toLowerCase() : 'notalla');
        const productoExistente = carrito.find(item => item.itemId === itemId);

        if (productoExistente) {
            productoExistente.cantidad = (productoExistente.cantidad || 1) + 1;
        } else {
            carrito.push({ ...producto, cantidad: 1, itemId: itemId });
        }

        localStorage.setItem('carrito', JSON.stringify(carrito));
        alert(`Producto "${producto.nombre}" añadido al carrito.`);
        actualizarContadorCarrito();
        mostrarCarritoLateral();

        if (typeof mostrarItemsEnCarritoCompra === 'function') {
            mostrarItemsEnCarritoCompra();
        }
    }

    window.mostrarCarritoLateral = function() {
        const carritoItemsContainerLateral = document.getElementById('carrito-items-lateral');
        carritoItemsContainerLateral.innerHTML = '';
        let carrito = localStorage.getItem('carrito');
        carrito = carrito ? JSON.parse(carrito) : [];
        let totalGeneral = 0;

        if (carrito.length === 0) {
            carritoItemsContainerLateral.innerHTML = '<p class="empty-cart">No hay productos en el carrito.</p>';
        } else {
            carrito.forEach(item => {
                const productoDiv = document.createElement('div');
                productoDiv.classList.add('carrito-item-lateral');

                const precioNumerico = parseFloat(item.precio.replace(/S\/ /, ''));
                const totalItem = precioNumerico * (item.cantidad || 1);
                totalGeneral += totalItem;

                productoDiv.innerHTML = `
                    <img src="${item.imagenSrc}" alt="${item.nombre}">
                    <div class="item-details-lateral">
                        <p class="item-nombre-lateral">${item.nombre}</p>
                        ${item.talla ? `<p class="item-talla-lateral">Talla: ${item.talla.toUpperCase()}</p>` : ''}
                        <p class="item-cantidad-lateral">Cantidad: ${item.cantidad || 1}</p>
                        <p class="item-precio-total-lateral">Total: S/ ${totalItem.toFixed(2)}</p>
                    </div>
                    <button class="eliminar-item-lateral" data-item-id="${item.itemId}">
                        <i class="fas fa-trash-alt"></i> Eliminar
                    </button>
                `;
                carritoItemsContainerLateral.appendChild(productoDiv);
            });

            document.querySelectorAll('.eliminar-item-lateral').forEach(button => {
                button.addEventListener('click', function() {
                    const itemIdToRemove = this.dataset.itemId;
                    eliminarDelCarritoLateral(itemIdToRemove);
                });
            });
        }
        document.getElementById('total-precio-lateral').textContent = `S/ ${totalGeneral.toFixed(2)}`;
    }

    window.eliminarDelCarritoLateral = function(itemIdToRemove) {
        let carrito = localStorage.getItem('carrito');
        carrito = carrito ? JSON.parse(carrito) : [];
        carrito = carrito.filter(item => item.itemId !== itemIdToRemove);
        localStorage.setItem('carrito', JSON.stringify(carrito));
        actualizarContadorCarrito();
        mostrarCarritoLateral();
        if (typeof mostrarItemsEnCarritoCompra === 'function') {
            mostrarItemsEnCarritoCompra();
        }
    }

    carritoIconLink.addEventListener('click', (event) => {
        event.preventDefault();
        carritoLateral.classList.toggle('active');
        mostrarCarritoLateral();
    });

    document.addEventListener('click', (event) => {
        if (!carritoLateral.contains(event.target) && !carritoIconLink.contains(event.target)) {
            carritoLateral.classList.remove('active');
        }
    });

    const productosGrid = document.querySelector('.producto-grid');
    if (productosGrid) {
        productosGrid.addEventListener('click', (event) => {
            if (event.target.closest('.cart-btn')) {
                const productoDiv = event.target.closest('.producto');
                if (productoDiv) {
                    const imagenSrc = productoDiv.querySelector('img').src;
                    // Asegúrate de obtener el nombre y precio correctamente del p
                    const pElement = productoDiv.querySelector('p');
                    const nombre = pElement.childNodes[0].textContent.trim(); // El nombre es el primer nodo de texto
                    const precioText = pElement.textContent.match(/S\/ (\d+\.\d{2})|\d+/); // Regex para S/ XX.XX o S/ XX
                    const precio = precioText ? precioText[0] : '';
                    const tallaElement = productoDiv.querySelector('.product-tallas span'); // Si tienes un elemento de talla en el producto
                    const talla = tallaElement ? tallaElement.textContent.trim() : null;

                    const producto = { imagenSrc, nombre, precio, talla }; // Pasa también la talla
                    agregarAlCarrito(producto);
                }
            }
        });
    }

    carritoLateral.addEventListener('click', (event) => {
        if (event.target.classList.contains('eliminar-item-lateral')) {
            const itemIdToRemove = event.target.dataset.itemId; // Usar data-item-id
            eliminarDelCarritoLateral(itemIdToRemove);
        }
    });

    actualizarContadorCarrito(); // Inicializar el contador al cargar la página
    mostrarCarritoLateral(); // Mostrar el carrito al cargar la página (si hay items)


    // **INICIO - Código para el Filtrado de Productos (Integrado)**
    const filtros = document.querySelector('.filtros');

    if (filtros && productosGrid) {
        const productosOriginales = Array.from(productosGrid.children); // Guarda la lista original de productos

        filtros.addEventListener('change', function(event) {
            if (event.target.type === 'checkbox') {
                console.log('Checkbox cambiado:', event.target.parentNode.textContent.trim()); // Añade esta línea
                filtrarProductos();
            }
        });

        function filtrarProductos() {
            const filtrosActivos = {
                precio: obtenerFiltros('Precio'),
                talla: obtenerFiltros('Talla'),
                color: obtenerFiltros('Color'),
                material: obtenerFiltros('Material')
            };

            // Limpiar el grid de productos antes de mostrar los filtrados
            productosGrid.innerHTML = '';

            productosOriginales.forEach(productoElement => {
                const dataPrecioTexto = productoElement.querySelector('p').textContent.match(/S\/ (\d+\.?\d*)/); // Mejorar regex para decimales
                const dataPrecio = dataPrecioTexto ? parseFloat(dataPrecioTexto[1]) : null;
                
                // Asegúrate de que tus productos HTML tengan data-talla, data-color, data-material
                const dataTalla = productoElement.dataset.talla ? productoElement.dataset.talla.trim() : '';
                const dataColor = productoElement.dataset.color ? productoElement.dataset.color.trim() : '';
                const dataMaterial = productoElement.dataset.material ? productoElement.dataset.material.trim() : '';
                
                let mostrarProducto = true;

                // Filtrar por Precio
                if (filtrosActivos.precio.length > 0) {
                    let cumplePrecio = false;
                    if (dataPrecio !== null) {
                        filtrosActivos.precio.forEach(rango => {
                            if (rango === 'S/0 - S/100' && dataPrecio >= 0 && dataPrecio <= 100) {
                                cumplePrecio = true;
                            } else if (rango === 'S/100 - S/200' && dataPrecio > 100 && dataPrecio <= 200) {
                                cumplePrecio = true;
                            } else if (rango === 'S/200+' && dataPrecio > 200) {
                                cumplePrecio = true;
                            }
                        });
                    }
                    if (!cumplePrecio) {
                        mostrarProducto = false;
                    }
                }

                // Filtrar por Talla
                if (filtrosActivos.talla.length > 0) {
                    const tallasProducto = dataTalla.split(',').map(t => t.trim()); // Divide y limpia espacios
                    const cumpleTalla = filtrosActivos.talla.some(tallaFiltro => tallasProducto.includes(tallaFiltro));
                    if (!cumpleTalla) {
                        mostrarProducto = false;
                    }
                }

                // Filtrar por Color
                if (filtrosActivos.color.length > 0) {
                    const coloresProducto = dataColor.split(',').map(c => c.trim()); // Divide y limpia espacios
                    const cumpleColor = filtrosActivos.color.some(colorFiltro => coloresProducto.includes(colorFiltro));
                    if (!cumpleColor) {
                        mostrarProducto = false;
                    }
                }

                // Filtrar por Material
                if (filtrosActivos.material.length > 0) {
                    const materialesProducto = dataMaterial.split(',').map(m => m.trim());
                    const cumpleMaterial = filtrosActivos.material.some(materialFiltro => materialesProducto.includes(materialFiltro));
                    if (!cumpleMaterial) {
                        mostrarProducto = false;
                    }
                }

                if (mostrarProducto) {
                    productosGrid.appendChild(productoElement.cloneNode(true));
                }
            });

            if (productosGrid.children.length === 0) {
                productosGrid.innerHTML = '<p>No se encontraron productos con los filtros seleccionados.</p>';
            }
        }

        function obtenerFiltros(categoria) {
            const filtrosCategoria = [];
            const divsConH4 = filtros.querySelectorAll('div h4');
            divsConH4.forEach(h4 => {
                if (h4.textContent.trim() === categoria) {
                    const divPadre = h4.parentNode;
                    if (divPadre) {
                        const checkboxes = divPadre.querySelectorAll('input[type="checkbox"]:checked');
                        checkboxes.forEach(checkbox => {
                            // Obtiene el texto del label asociado al checkbox, que es el valor del filtro
                            const label = divPadre.querySelector(`label[for="${checkbox.id}"]`);
                            if (label) {
                                filtrosCategoria.push(label.textContent.trim());
                            }
                        });
                    }
                }
            });
            return filtrosCategoria;
        }
    }
});