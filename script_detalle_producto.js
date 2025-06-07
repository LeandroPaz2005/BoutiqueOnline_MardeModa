document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');

    // Simulación de datos de productos (DEBES ACTUALIZAR ESTO CON TUS DATOS REALES)
    const productos = {
        'polo1': { nombre: 'Polo verde', precio: 'S/ 100', imagen: 'img/blusa1.jpg', descripcion: 'Descripción de el polo verde.', tallas: ['M'] },
        'polo2': { nombre: 'Polo celeste', precio: 'S/ 110', imagen: 'img/blusa1.jpg', descripcion: 'Descripción de el polo celeste.', tallas: ['L'] },
        'polo3': { nombre: 'Polo gris', precio: 'S/ 120', imagen: 'img/blusa1.jpg', descripcion: 'Descripción de el polo gris.', tallas: ['M'] },
        'polo4': { nombre: 'Polo amarillo', precio: 'S/130', imagen: 'img/blusa1.jpg', descripcion: 'Descripción de el polo amarillo.', tallas: ['XS', 'S']},
        'polo5': { nombre: 'Polo rojo', precio: 'S/ 140', imagen: 'img/blusa1.jpg', descripcion: 'Descripción de el polo rojo.', tallas: ['M', 'L'] },
        'polo6': { nombre: 'Polo azul', precio: 'S/ 150', imagen: 'img/blusa1.jpg', descripcion: 'Descripción de el polo azul.', tallas: ['L', 'XL'] },
        'polo7': { nombre: 'Polo negro', precio: 'S/ 160', imagen: 'img/blusa1.jpg', descripcion: 'Descripción de el polo negro.', tallas: ['S', 'M'] },
        'polo8': { nombre: 'Polo blanco', precio: 'S/ 170', imagen: 'img/blusa1.jpg', descripcion: 'Descripción de el polo blanco.', tallas: ['XS', 'S'] },
        // ... Agrega aquí la información de todos tus polos (asegúrate de que los IDs coincidan con los de los enlaces en polos.html)
        'blusa1': { nombre: 'Blusa Verde', precio: 'S/ 120', imagen: 'img/blusa1.jpg', descripcion: 'Una elegante blusa verde esmeralda de algodón, ideal para cualquier ocasión.', tallas: ['M'] },
        'blusa2': { nombre: 'Blusa Azul', precio: 'S/ 150', imagen: 'img/blusa1.jpg', descripcion: 'Blusa de lino color azul cielo, fresca y cómoda para el día a día.', tallas: ['L'] },
        'blusa3': { nombre: 'Blusa Rosada', precio: 'S/ 180', imagen: 'img/blusa1.jpg', descripcion: 'Exquisita blusa de seda en tono rosa palo, perfecta para eventos especiales.', tallas: ['S'] },
        'blusa4': { nombre: 'Blusa Negra', precio: 'S/ 135', imagen: 'img/blusa1.jpg', descripcion: 'Blusa negra clásica de poliéster, un básico versátil para tu armario.', tallas: ['XL'] },
        'blusa5': { nombre: 'Blusa Blanca', precio: 'S/ 110', imagen: 'img/blusa1.jpg', descripcion: 'Blusa básica blanca de algodón, cómoda y fácil de combinar.', tallas: ['M'] },
        'blusa6': { nombre: 'Blusa Amarilla', precio: 'S/ 160', imagen: 'img/blusa1.jpg', descripcion: 'Blusa de viscosa en un vibrante amarillo mostaza, ideal para destacar.', tallas: ['S'] },
        'blusa7': { nombre: 'Blusa Roja', precio: 'S/ 195', imagen: 'img/blusa1.jpg', descripcion: 'Impactante blusa de gasa roja pasión, perfecta para una noche especial.', tallas: ['L'] },
        'blusa8': { nombre: 'Blusa Morada', precio: 'S/ 145', imagen: 'img/blusa1.jpg', descripcion: 'Encantadora blusa de rayón en tono morado lavanda, suave y femenina.', tallas: ['M'] },
        // ... Agrega aquí la información de todas tus blusas (asegúrate de que los IDs coincidan con los de los enlaces en blusas.html)
        'top1': { nombre: 'Top Negro', precio: 'S/ 65', imagen: 'img/blusa1.jpg', descripcion: 'Un top negro básico de algodón, ideal para cualquier look.', tallas: ['S'] },
        'top2': { nombre: 'Top Blanco', precio: 'S/ 80', imagen: 'img/blusa1.jpg', descripcion: 'Un top blanco de seda, suave y elegante.', tallas: ['M'] },
        'top3': { nombre: 'Top Gris', precio: 'S/ 70', imagen: 'img/blusa1.jpg', descripcion: 'Top gris cómodo de algodón, perfecto para el día a día.', tallas: ['L'] },
        'top4': { nombre: 'Top Rosado', precio: 'S/ 50', imagen: 'img/blusa1.jpg', descripcion: 'Top rosado ligero de algodón, un toque de color para tu outfit.', tallas: ['XS'] },
        'top5': { nombre: 'Top Beige', precio: 'S/ 88', imagen: 'img/blusa1.jpg', descripcion: 'Un top beige de algodón, versátil y fácil de combinar.', tallas: ['M'] },
        'top6': { nombre: 'Top Azul', precio: 'S/ 60', imagen: 'img/blusa1.jpg', descripcion: 'Top azul de seda, ideal para un estilo sofisticado.', tallas: ['S'] },
        'top7': { nombre: 'Top Rojo', precio: 'S/ 105', imagen: 'img/blusa1.jpg', descripcion: 'Top rojo de seda, perfecto para una noche especial.', tallas: ['L'] },
        'top8': { nombre: 'Top Verde', precio: 'S/ 90', imagen: 'img/blusa1.jpg', descripcion: 'Top verde de algodón, fresco y casual.', tallas: ['M'] },
        // ... Agrega aquí la información de todas tus otros (asegúrate de que los IDs coincidan con los de los enlaces en otros.html)
        'otros1': { nombre: 'Otros verde', precio: 'S/ 100', imagen: 'img/blusa1.jpg', descripcion: 'Un producto diverso en color verde, disponible en varias tallas.', tallas: ['M', 'L', 'XL'] },
        'otros2': { nombre: 'Otros rojo', precio: 'S/ 135', imagen: 'img/blusa1.jpg', descripcion: 'Un producto rojo versátil, con opciones de talla para ajustarse a ti.', tallas: ['XS', 'M'] },
        'otros3': { nombre: 'Otros amarillo', precio: 'S/ 175', imagen: 'img/blusa1.jpg', descripcion: 'Producto amarillo vibrante, ideal para quienes buscan algo único.', tallas: ['S', 'M'] },
        'otros4': { nombre: 'Otros gris', precio: 'S/ 130', imagen: 'img/blusa1.jpg', descripcion: 'Un producto gris elegante de seda, perfecto para añadir un toque sofisticado.',tallas: ['M'] },
        'otros5': { nombre: 'Otros blanco', precio: 'S/ 120', imagen: 'img/blusa1.jpg', descripcion: 'Producto blanco clásico de seda, un básico esencial.', tallas: ['L', 'XL'] },
        'otros6': { nombre: 'Otros marrón', precio: 'S/ 150', imagen: 'img/blusa1.jpg', descripcion: 'Un producto marrón de algodón, cómodo y natural.', tallas: ['S', 'M'] },
        'otros7': { nombre: 'Otros azul', precio: 'S/ 180', imagen: 'img/blusa1.jpg', descripcion: 'Producto azul de algodón, con un estilo fresco y relajado.', tallas: ['S', 'L'] },
        'otros8': { nombre: 'Otros negro', precio: 'S/ 200', imagen: 'img/blusa1.jpg', descripcion: 'Un producto negro atemporal de algodón, imprescindible en cualquier colección.', tallas: ['L'] }
    };
    // ***** Nueva lógica para actualizar la categoría *****
    // Mapa de prefijos de ID a nombres de categoría y sus URLs
    const categorias = {
        'polo': { nombre: 'Polos', url: 'polos.html' },
        'blusa': { nombre: 'Blusas', url: 'blusas.html' },
        'tops': { nombre: 'Tops', url: 'tops.html' },
        'otro': { nombre: 'Otros', url: 'otros.html' }
    };

    // Extraer la categoría del productId
    const categoriaKey = productId ? productId.match(/^[a-zA-Z]+/)?.[0] : '';
    const categoria = categorias[categoriaKey] || { nombre: 'Categoría no definida', url: '#' };

    // Actualizar el elemento <a id="categoria-actual">
    const categoriaElement = document.getElementById('categoria-actual');
    if (categoriaElement) {
        categoriaElement.textContent = categoria.nombre;
        categoriaElement.href = categoria.url;
    } else {
        console.error("No se encontró el elemento con id 'categoria-actual'.");
    }
    // ***** Fin de la nueva lógica *****

    const nombreProductoElement = document.getElementById('nombre-producto');
    const precioProductoElement = document.getElementById('precio-producto');
    const imagenProductoElement = document.getElementById('imagen-principal');
    const descripcionProductoElement = document.getElementById('descripcion-producto');
    // ESTA ES LA ÚNICA DECLARACIÓN DE tallasDisponiblesDiv que debe existir en este script
    const tallasDisponiblesDiv = document.getElementById('tallas-disponibles');
    const todasLasTallas = ['XS', 'S', 'M', 'L', 'XL']; // Define todas las tallas posibles

    if (productId && productos[productId]) {
        const producto = productos[productId];
        nombreProductoElement.textContent = producto.nombre;
        precioProductoElement.textContent = producto.precio;
        imagenProductoElement.src = producto.imagen;
        imagenProductoElement.alt = producto.nombre;
        descripcionProductoElement.textContent = producto.descripcion;

        if (producto.tallas && tallasDisponiblesDiv) {
            // Limpia cualquier botón de talla existente
            tallasDisponiblesDiv.innerHTML = '';

            todasLasTallas.forEach(talla => {
                const botonTalla = document.createElement('button');
                botonTalla.textContent = talla;
                botonTalla.dataset.talla = talla; // <--- ¡CORRECCIÓN: Añadido data-talla aquí!

                if (producto.tallas.includes(talla)) {
                    // La talla está disponible, no deshabilitamos el botón
                } else {
                    // La talla no está disponible, deshabilita el botón
                    botonTalla.disabled = true;
                }
                tallasDisponiblesDiv.appendChild(botonTalla);
            });
        } else if (tallasDisponiblesDiv) {
            tallasDisponiblesDiv.innerHTML = '<p>Tallas no especificadas para este producto.</p>';
        }

        // Aquí podrías actualizar otros elementos de la página de detalles
    } else {
        const mainElement = document.querySelector('.detalle-producto-container');
        if (mainElement) {
            mainElement.innerHTML = '<p>Producto no encontrado.</p>';
        } else {
            console.error("No se encontró el elemento con la clase 'detalle-producto-container'.");
        }
    }

    // Carrito desplegable (esta parte debería ir en script.js si sigues la recomendación de unificación)
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
        window.location.href = 'comprar.html';
    });
    carritoLateral.appendChild(comprarButtonLateral);

    const vaciarButtonLateral = document.createElement('button');
    vaciarButtonLateral.textContent = 'Vaciar Carrito';
    vaciarButtonLateral.classList.add('vaciar-btn-lateral');
    vaciarButtonLateral.addEventListener('click', () => {
        localStorage.removeItem('carrito'); // Limpia el carrito en localStorage
        // Si usas una clave 'carritoCompraActualizada' en carrito.js, también la eliminas aquí
        // localStorage.removeItem('carritoCompraActualizada');

        // **** APLICANDO PASO 5: SINCRONIZAR VACIADO CON carrito.html ****
        if (typeof vaciarCarritoCompleto === 'function') {
            vaciarCarritoCompleto(); // Llama a la función de carrito.js para vaciar y actualizar carrito.html
        }
        // Si no estamos en carrito.html o si vaciarCarritoCompleto no existe,
        // actualizamos la barra lateral y el contador de todas formas.
        actualizarContadorCarrito();
        mostrarCarritoLateral();
        // **** FIN PASO 5 ****
    });
    carritoLateral.appendChild(vaciarButtonLateral);

    function actualizarContadorCarrito() {
        let carrito = localStorage.getItem('carrito');
        carrito = carrito ? JSON.parse(carrito) : [];
        const contador = carrito.reduce((acc, item) => acc + (item.cantidad || 1), 0); // Suma las cantidades

        const contadorElementHeader = carritoIconLink.querySelector('#carrito-count-header');
        if (contadorElementHeader) {
            contadorElementHeader.textContent = `(${contador})`;
        }

        // Descomentamos esta sección para actualizar el contador en los botones de "Agregar"
        // Esta parte es más apropiada para un script general que maneja todos los botones de producto
        // Si tienes script.js, considera mover esto allí.
        const botonesCarrito = document.querySelectorAll('.producto .cart-btn');
        botonesCarrito.forEach(boton => {
            let contadorSpan = boton.querySelector('.carrito-contador');
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

    function agregarAlCarrito(producto) {
        let carrito = localStorage.getItem('carrito');
        carrito = carrito ? JSON.parse(carrito) : [];

        // **** APLICANDO PASO 1: Generación de itemId y manejo de cantidades ****
        // Asegúrate de que 'producto' tenga 'nombre', 'precio', 'imagenSrc' y 'talla'
        const itemId = producto.nombre.replace(/\s+/g, '-').toLowerCase() + '-' + (producto.talla ? producto.talla.replace(/\s+/g, '-').toLowerCase() : 'notalla');

        const productoExistente = carrito.find(item => item.itemId === itemId);

        if (productoExistente) {
            // Si el producto ya existe con la misma talla, solo incrementa su cantidad
            productoExistente.cantidad = (productoExistente.cantidad || 1) + 1;
        } else {
            // Si el producto no existe, añádelo con cantidad 1 y su itemId
            carrito.push({ ...producto, cantidad: 1, itemId: itemId });
        }
        // **** FIN PASO 1 ****

        localStorage.setItem('carrito', JSON.stringify(carrito));
        alert(`Producto "${producto.nombre}" añadido al carrito.`); // Considera reemplazar esto por una notificación menos intrusiva
        actualizarContadorCarrito();
        mostrarCarritoLateral(); // Asegúrate de que se llame aquí para actualizar el carrito visualmente

        // Sincronizar con carrito.html si estamos en esa página (Paso 4.2)
        if (typeof mostrarItemsEnCarritoCompra === 'function') {
            mostrarItemsEnCarritoCompra();
        }
    }

    function mostrarCarritoLateral() {
        // console.log("Mostrando carrito lateral..."); // Para depuración
        const carritoItemsContainerLateral = document.getElementById('carrito-items-lateral'); // Este ya está declarado arriba

        carritoItemsContainerLateral.innerHTML = ''; // Limpia el contenido actual
        let carrito = localStorage.getItem('carrito');
        carrito = carrito ? JSON.parse(carrito) : [];
        let totalGeneral = 0;

        if (carrito.length === 0) {
            carritoItemsContainerLateral.innerHTML = '<p class="empty-cart">No hay productos en el carrito.</p>';
        } else {
            // *** IMPORTANTE: Ya NO agrupamos por nombre. El array 'carrito' ya tiene 'cantidad' y 'itemId'. ***
            carrito.forEach(item => { // Itera directamente sobre el array 'carrito'
                const productoDiv = document.createElement('div');
                productoDiv.classList.add('carrito-item-lateral');

                // Asegúrate de que item.precio sea un string con "S/ " para parsear
                const precioNumerico = parseFloat(item.precio.replace(/S\/ /, ''));
                const totalItem = precioNumerico * (item.cantidad || 1); // Usa item.cantidad
                totalGeneral += totalItem;

                // **** APLICANDO PASO 2: USAR data-item-id en el botón de eliminar ****
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
                // **** FIN PASO 2 ****

                carritoItemsContainerLateral.appendChild(productoDiv);
            });

            // **** APLICANDO PASO 3: ADJUNTAR EVENT LISTENERS A LOS BOTONES DE ELIMINAR ****
            // Este bloque es CRUCIAL y debe ir AQUÍ, después de que todos los botones han sido agregados al DOM.
            document.querySelectorAll('.eliminar-item-lateral').forEach(button => {
                button.addEventListener('click', function() {
                    const itemIdToRemove = this.dataset.itemId; // Obtiene el itemId del atributo data-item-id
                    eliminarDelCarritoLateral(itemIdToRemove); // Llama a la función de eliminación (Paso 4)
                });
            });
            // **** FIN PASO 3 ****
        }

        document.getElementById('total-precio-lateral').textContent = `S/ ${totalGeneral.toFixed(2)}`;
    }

    function eliminarDelCarritoLateral(itemIdToRemove) {
        let carrito = localStorage.getItem('carrito');
        carrito = carrito ? JSON.parse(carrito) : [];

        // **** APLICANDO PASO 4.1: FILTRAR EL CARRITO POR itemId ****
        // Esto crea un NUEVO array que excluye el ítem con el itemId coincidente.
        carrito = carrito.filter(item => item.itemId !== itemIdToRemove);
        // **** FIN PASO 4.1 ****

        localStorage.setItem('carrito', JSON.stringify(carrito)); // Guarda el carrito actualizado

        actualizarContadorCarrito(); // Actualiza el número de ítems en el icono del carrito
        mostrarCarritoLateral(); // Redibuja la barra lateral (quita el ítem eliminado visualmente)

        // **** APLICANDO PASO 4.2: SINCRONIZAR CON carrito.html ****
        // Comprueba si la función 'mostrarItemsEnCarritoCompra' (de carrito.js) está disponible.
        if (typeof mostrarItemsEnCarritoCompra === 'function') {
            mostrarItemsEnCarritoCompra(); // Llama a la función de carrito.js para refrescar la vista principal.
        }
        // **** FIN PASO 4.2 ****
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
                    const [nombre, precio] = productoDiv.querySelector('p').innerHTML.split('<br>');
                    // Si tus productos en el grid tienen tallas, deberías intentar obtenerlas aquí también.
                    // Para simplificar, asumimos que en el grid principal no se selecciona talla directamente.
                    const producto = { imagenSrc, nombre, precio };
                    agregarAlCarrito(producto);
                }
            }
        });
    }

    // ELIMINADA: Esta parte es redundante ya que el event listener se adjunta en mostrarCarritoLateral()
    // carritoLateral.addEventListener('click', (event) => {
    //     if (event.target.classList.contains('eliminar-item-lateral')) {
    //         const nombreAEliminar = event.target.dataset.nombre; // Esto debería ser dataset.itemId
    //         eliminarDelCarritoLateral(nombreAEliminar);
    //     }
    // });


    // Event listener para el botón "Agregar al carrito" en detalle_producto.html
    const agregarAlCarritoBtnDetalle = document.querySelector('.detalles-producto .cart-btn');
    // REMOVIDA: La declaración de tallasDisponiblesDiv ya está al principio del script
    // const tallasDisponiblesDiv = document.getElementById('tallas-disponibles'); 

    if (agregarAlCarritoBtnDetalle) {
        agregarAlCarritoBtnDetalle.addEventListener('click', function() {
            const nombre = document.getElementById('nombre-producto').textContent;
            const precioTexto = document.getElementById('precio-producto').textContent;
            const imagenSrc = document.getElementById('imagen-principal').src;

            const precioMatch = precioTexto.match(/S\/ (\d+\.?\d*)/);
            const precio = precioMatch ? precioMatch[0] : precioTexto;

            // *** Capturar la talla seleccionada ***
            let tallaSeleccionada = '';
            // Asegúrate de que tallasDisponiblesDiv no sea null antes de querySelectorAll
            if (tallasDisponiblesDiv) {
                const botonTallaActivo = tallasDisponiblesDiv.querySelector('button.active'); // Busca el botón con la clase 'active'
                if (botonTallaActivo) {
                    tallaSeleccionada = botonTallaActivo.textContent;
                } else {
                    // Opcional: Si no se selecciona ninguna talla, y el producto tiene tallas,
                    // podrías forzar la selección de la primera disponible o mostrar una alerta.
                    // Por ahora, si no se selecciona, se usará 'notalla' en el itemId.
                    const productoActual = productos[productId];
                    if (productoActual && productoActual.tallas && productoActual.tallas.length > 0) {
                        alert('Por favor, selecciona una talla antes de agregar al carrito.');
                        return; // Detiene la ejecución si no se selecciona talla
                    }
                }
            }


            const producto = {
                nombre: nombre,
                precio: precio,
                imagenSrc: imagenSrc,
                talla: tallaSeleccionada // <-- ¡Pasa la talla aquí!
            };

            agregarAlCarrito(producto);
        });
    }

    // *** Lógica para seleccionar talla (Asegúrate de que tus botones de talla puedan ser "activos") ***
    // Añade esta lógica cerca de donde procesas las tallas en script_detalle_producto.js
    // Esto hará que los botones de talla sean interactivos y mantengan un estado.
    if (tallasDisponiblesDiv) {
        tallasDisponiblesDiv.addEventListener('click', function(event) {
            if (event.target.tagName === 'BUTTON' && !event.target.disabled) {
                // Remover 'active' de todos los botones de talla
                tallasDisponiblesDiv.querySelectorAll('button').forEach(btn => {
                    btn.classList.remove('active');
                });
                // Añadir 'active' al botón clicado
                event.target.classList.add('active');
            }
        });

        // Opcional: Seleccionar una talla por defecto si solo hay una disponible o si no se ha seleccionado ninguna
        const productoActual = productos[productId]; // 'productos' viene de tu mapa de datos en script_detalle_producto.js
        if (productoActual && productoActual.tallas && productoActual.tallas.length > 0) {
            // Buscamos el primer botón que corresponda a una talla disponible del producto
            // y que no esté deshabilitado.
            const primerTallaDisponible = productoActual.tallas[0];
            const primerBotonTalla = tallasDisponiblesDiv.querySelector(`button:not([disabled])[data-talla="${primerTallaDisponible}"]`);
            if (primerBotonTalla) {
                primerBotonTalla.classList.add('active');
            }
        }
    }

    // Inicializar el contador del carrito y mostrar el carrito lateral (si está activo) al cargar la página.
    // Es buena práctica llamar a esto aquí, aunque la lógica completa esté en funciones.
    actualizarContadorCarrito();
    // No llames a mostrarCarritoLateral() aquí si no quieres que el carrito lateral se abra por defecto
    // cada vez que la página carga. Solo debe abrirse cuando el usuario hace click en el icono.
    // Si quieres que se muestre con items al cargar, mantenlo.
    // mostrarCarritoLateral();

});