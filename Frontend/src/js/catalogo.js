import { obtenerOfertas } from './services/ofertas.js';

// Función para mostrar los productos en el catálogo
function mostrarCatalogo(datos) {
    const catalogo = document.getElementById('catalogo');
    datos.forEach(producto => {
        const card = document.createElement('div');
        card.classList.add('col');
        card.innerHTML = `
            <div class="card h-100">
                <img src="${producto.imagen}" class="card-img-top" alt="${producto.nombre}">
                <div class="card-body">
                    <h5 class="card-title">${producto.nombre}</h5>
                    <p class="card-text">${producto.descripcion}</p>
                    <p class="card-text"><strong>${producto.puntos} Puntos</strong></p>
                </div>
                <div class="card-footer text-center">
                    <button class="btn btn-primary">Intercambiar</button>
                </div>
            </div>
        `;
        catalogo.appendChild(card);
    });
}

// Iniciar la carga y visualización del catálogo al cargar el script
document.addEventListener('DOMContentLoaded', () => {
    obtenerOfertas().then(datos => {
        mostrarCatalogo(datos);
    });
});