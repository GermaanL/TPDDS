import { crearMapa } from "./map.js";
import { obtenerRecomendaciones } from './services/recomendaciones.js';

let mapVisible = false; // Control de visibilidad

document.getElementById('btnRecomendarPuntos').addEventListener('click', () => {
    const calle = document.getElementById('inputCalle').value;
    const numero = document.getElementById('inputNumero').value;
    const localidad = document.getElementById('inputLocalidad').value;

    // Construir la dirección completa
    const direccionCompleta = `${calle} ${numero}, ${localidad}`;
    const mapContainer = document.getElementById('mapContainer');

    // Alternar visibilidad del contenedor del mapa
    if (mapVisible) {
        mapContainer.style.display = 'none'; // Oculta el mapa
        mapVisible = false;
    } else {
        // Muestra el mapa y obtiene las recomendaciones
        mapContainer.style.display = 'block'; 
        mapVisible = true;

        obtenerRecomendaciones(direccionCompleta).then(datosRecomendados => {
            crearMapa(datosRecomendados, 'recomendacion.png'); // Llama a la función para crear el mapa con los puntos recomendados
        });
    }
});
