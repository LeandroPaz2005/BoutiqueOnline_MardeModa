// Define vaciarCarritoCompleto en el ámbito global para que otros scripts puedan acceder a ella
function vaciarCarritoCompleto() {
    // 1. Limpiar el array local del carrito
    carritoCompra = [];

    // 2. Limpiar el localStorage
    localStorage.removeItem('carrito'); // Borra la entrada 'carrito'
    localStorage.removeItem('carritoCompraActualizada'); // Si la usas para el checkout

    // 3. Actualizar la interfaz de usuario de la página carrito.html
    const listaItemsCarrito = document.querySelector('.lista-items-carrito');
    if (listaItemsCarrito) {
        listaItemsCarrito.innerHTML = '<p>El carrito está vacío.</p>';
    }

    // 4. Ocultar el resumen de compra y actualizar los totales a cero
    ocultarResumenCompra();
    actualizarResumenCompra(); // Esto pondrá los totales a S/ 0.00 y el contador a (0)

    // Opcional: Cerrar la barra lateral si está abierta (esto asume que carritoLateral es global o se obtiene aquí)
    const carritoLateral = document.getElementById('carrito-lateral');
    if (carritoLateral && carritoLateral.classList.contains('active')) {
        carritoLateral.classList.remove('active');
    }

    alert('El carrito ha sido vaciado.');
    // Si necesitas que el carrito lateral también se actualice, llama a su función
    // if (typeof mostrarCarritoLateral === 'function') {
    //     mostrarCarritoLateral();
    //     actualizarContadorCarrito();
    // }
}

// *** IMPORTANTE: La función eliminarItemIndividual también debe ser global
function eliminarItemIndividual(itemIdToRemove) {
    // 1. Filtrar el array local carritoCompra para remover el item
    carritoCompra = carritoCompra.filter(item =>
        (item.nombre.replace(/\s+/g, '-').toLowerCase() + '-' + (item.talla ? item.talla.replace(/\s+/g, '-').toLowerCase() : 'notalla')) !== itemIdToRemove
    );

    // 2. Guardar el carrito actualizado en localStorage
    localStorage.setItem('carrito', JSON.stringify(carritoCompra));

    // 3. Volver a mostrar los items en el carrito para reflejar el cambio en la UI de carrito.html
    mostrarItemsEnCarritoCompra(); // Esta función ya refresca la UI y los totales

    // 4. Opcional: Si la barra lateral existe, actualízala también
    if (typeof mostrarCarritoLateral === 'function') { // Comprueba si script.js está cargado
        mostrarCarritoLateral();
        actualizarContadorCarrito();
    }
    alert('Artículo eliminado del carrito.');
}


document.addEventListener('DOMContentLoaded', function() {
    mostrarItemsEnCarritoCompra(); // Esto se ejecuta al cargar la página

    const listaItemsCarrito = document.querySelector('.lista-items-carrito');
    if (listaItemsCarrito) {
        listaItemsCarrito.addEventListener('change', function(event) {
            if (event.target.matches('input[type="number"]')) {
                actualizarCantidadYTotal(event.target);
            }
        });
    }

    // --- CÓDIGO PARA EL BOTÓN VACIAR CARRITO EN carrito.html ---
    const vaciarCarritoBtn = document.querySelector('.vaciar-carrito-btn');
    if (vaciarCarritoBtn) {
        vaciarCarritoBtn.addEventListener('click', function() {
            vaciarCarritoCompleto(); // Llama a la función global
        });
    }
    // ----------------------------------------------------
});

let carritoCompra = []; // Variable para almacenar los items del carrito en la página

function mostrarItemsEnCarritoCompra() {
    const listaItemsCarrito = document.querySelector('.lista-items-carrito');
    listaItemsCarrito.innerHTML = ''; // Limpia el contenido actual

    let carrito = localStorage.getItem('carrito');
    carritoCompra = carrito ? JSON.parse(carrito) : []; // Inicializa carritoCompra

    if (carritoCompra.length === 0) {
        listaItemsCarrito.innerHTML = '<p>El carrito está vacío.</p>';
        ocultarResumenCompra();
    } else {
        carritoCompra.forEach(item => {
            const itemCarritoDiv = document.createElement('div');
            itemCarritoDiv.classList.add('item-carrito');
            const itemId = item.nombre.replace(/\s+/g, '-').toLowerCase() + '-' + (item.talla ? item.talla.replace(/\s+/g, '-').toLowerCase() : 'notalla');
            
            const cantidadActual = item.cantidad || 1;

            itemCarritoDiv.innerHTML = `
                <div class="imagen-carrito">
                    <img src="${item.imagenSrc}" alt="${item.nombre}">
                </div>
                <div class="detalles-carrito">
                    <h3 class="nombre-producto-carrito">${item.nombre}</h3>
                    ${item.talla ? `<p class="talla-carrito">Talla: ${item.talla}</p>` : ''}
                    <p class="precio-unitario" data-precio="${parseFloat(item.precio.replace(/S\/ /, ''))}">${item.precio}</p>
                </div>
                <div class="cantidad-carrito">
                    <label for="cantidad-${itemId}">Cantidad:</label>
                    <input type="number" id="cantidad-${itemId}" value="${cantidadActual}" min="1" data-item-id="${itemId}">
                </div>
                <div class="total-item">
                    Total: <span id="total-item-${itemId}">${(parseFloat(item.precio.replace(/S\/ /, '')) * cantidadActual).toFixed(2)}</span>
                </div>
                <button class="eliminar-item-btn" data-item-id="${itemId}">
                    Eliminar
                </button>
            `;
            listaItemsCarrito.appendChild(itemCarritoDiv);
        });

        // **** CLAVE: ADJUNTAR EVENT LISTENERS AQUÍ, DESPUÉS DE CREAR LOS ELEMENTOS ****
        document.querySelectorAll('.eliminar-item-btn').forEach(button => {
            button.addEventListener('click', function() {
                eliminarItemIndividual(this.dataset.itemId);
            });
        });

        actualizarResumenCompra();
        mostrarResumenCompra();
    }
}

function actualizarCantidadYTotal(inputCantidad) {
    const itemId = inputCantidad.dataset.itemId;
    const itemCarritoDiv = inputCantidad.closest('.item-carrito');
    const precioUnitarioElement = itemCarritoDiv.querySelector('.precio-unitario');
    const totalItemElement = itemCarritoDiv.querySelector(`#total-item-${itemId}`);
    const cantidad = parseInt(inputCantidad.value);
    const precioUnitario = parseFloat(precioUnitarioElement.dataset.precio);
    const totalItem = cantidad * precioUnitario;

    if (totalItemElement) {
        totalItemElement.textContent = totalItem.toFixed(2);
    }

    const itemEnCarrito = carritoCompra.find(item =>
        (item.nombre.replace(/\s+/g, '-').toLowerCase() + '-' + (item.talla ? item.talla.replace(/\s+/g, '-').toLowerCase() : 'notalla')) === itemId
    );
    if (itemEnCarrito) {
        itemEnCarrito.cantidad = cantidad;
        localStorage.setItem('carrito', JSON.stringify(carritoCompra));
    }

    actualizarResumenCompra();
    // Opcional: Si quieres que el carrito lateral se actualice al cambiar cantidad
    // if (typeof mostrarCarritoLateral === 'function') {
    //     mostrarCarritoLateral();
    //     actualizarContadorCarrito();
    // }
}

function actualizarResumenCompra() {
    let subtotal = 0;
    let totalCantidadItems = 0;
    carritoCompra.forEach(item => {
        const precioNumerico = parseFloat(item.precio.replace(/S\/ /, ''));
        const cantidadItem = item.cantidad || 1;
        subtotal += precioNumerico * cantidadItem;
        totalCantidadItems += cantidadItem;
    });

    const subtotalElement = document.querySelector('.detalle-resumen:nth-child(2) p:last-child');
    const totalElement = document.querySelector('.detalle-resumen.total p:last-child');
    const descuentoElement = document.querySelector('.detalle-resumen:nth-child(1) p:last-child');
    const carritoCountHeader = document.getElementById('carrito-count-header');

    if (descuentoElement) {
        descuentoElement.textContent = `S/ 0.00`;
    }
    if (subtotalElement) {
        subtotalElement.textContent = `S/ ${subtotal.toFixed(2)}`;
    }
    if (totalElement) {
        totalElement.textContent = `S/ ${subtotal.toFixed(2)}`;
    }
    if (carritoCountHeader) {
        carritoCountHeader.textContent = `(${totalCantidadItems})`;
    }
}

function mostrarResumenCompra() {
    const resumenCompra = document.querySelector('.resumen-compra');
    if (resumenCompra) {
        resumenCompra.style.display = 'block';
    }
}

function ocultarResumenCompra() {
    const resumenCompra = document.querySelector('.resumen-compra');
    if (resumenCompra) {
        resumenCompra.style.display = 'none';
    }
}

const siguienteBtn = document.querySelector('.boton-continuar');
if (siguienteBtn) {
    siguienteBtn.addEventListener('click', function() {
        localStorage.setItem('carritoCompraActualizada', JSON.stringify(carritoCompra));
        window.location.href = 'identificacion.html';
    });
}