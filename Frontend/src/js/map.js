// mapa.js
import { obtenerHeladeras } from './services/heladeras.js';

let map;

export function crearMapa(datosHeladeras, nombreIconoMapa) {
    // Elimina el mapa si ya existe
    if (map) {
        map.remove();
    }

    // Inicializa el mapa
    map = L.map('map').setView([-34.55012980593437, -58.36869394495356], 10);

    L.tileLayer('https://api.maptiler.com/maps/basic-v2/{z}/{x}/{y}@2x.png?key=IVQEtcjkvUuNTleGu82d', {
        attribution: '<a href="https://www.maptiler.com/copyright/" target="_blank">&copy; MapTiler</a> <a href="https://www.openstreetmap.org/copyright" target="_blank">&copy; OpenStreetMap contributors</a>'
    }).addTo(map);

    var heladeraIcon = L.icon({
        iconUrl: 'src/img/' + nombreIconoMapa,
        iconSize: [35, 50],
        iconAnchor: [30, 50],
    });

    // Itera sobre los datos de heladeras y crea marcadores
    datosHeladeras.forEach(function (heladera) {
        var marker = L.marker([heladera.lat, heladera.lng], { icon: heladeraIcon }).addTo(map);
        marker.bindPopup(heladera.name);
    });
}

document.addEventListener('DOMContentLoaded', () => {
    obtenerHeladeras().then(datos => {
        crearMapa(datos, "marcadorHeladera.png");
    });
});

