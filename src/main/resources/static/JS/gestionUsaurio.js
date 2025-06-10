document.addEventListener('DOMContentLoaded', () => {
  const mensaje = document.getElementById('mensaje-oferta');

  if (mensaje) {
    mensaje.style.transition = 'background-color 0.5s ease, transform 0.3s ease';
    mensaje.addEventListener('mouseover', () => {
      mensaje.style.backgroundColor = '#ff4c94';
      mensaje.style.transform = 'scale(1.02)';
    });
    mensaje.addEventListener('mouseout', () => {
      mensaje.style.backgroundColor = '#a0004d';
      mensaje.style.transform = 'scale(1)';
    });
  }
});
