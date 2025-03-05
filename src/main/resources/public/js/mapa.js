
/*
// Crea un mapa centrado en la coordenada -34.55012980593437,-58.36869394495356 con un zoom de 7
var map = L.map('map').setView([-34.55012980593437, -58.36869394495356], 7);

// Crea una capa de mapa de tipo TileLayer
L.tileLayer('https://api.maptiler.com/maps/basic-v2/{z}/{x}/{y}@2x.png?key=IVQEtcjkvUuNTleGu82d', {
    attribution: '<a href="https://www.maptiler.com/copyright/" target="_blank">&copy; MapTiler</a> <a href="https://www.openstreetmap.org/copyright" target="_blank">&copy; OpenStreetMap contributors</a>'
}).addTo(map); // Agrega la capa de mapa al mapa

var heladeraIcon = L.icon({
    iconUrl: '/img/marcadorHeladera.png',
    iconSize: [35, 50],
    iconAnchor: [30, 50],
});

// Objeto que mapea coordenadas a contenido de popup
var popups = {
    "-34.65942907369565,-58.467921958535946": "UTN campus",
    "-34.59844621249422,-58.42008481621035": "UTN medrano",
    "-34.56593800505455,-58.606189218064046": "Kiosco Menes",
    "-34.62811586361482,-58.44650128737324": "UBA Filosofia",
    "-34.6035622548135,-58.38160258922659": "Obelisco"
};

// Selecciona el elemento UL donde se generarán los ítems de la lista
var heladeraList = document.getElementById('heladeraList');

// Itera sobre las coordenadas y agrega un marcador en cada una
Object.keys(popups).forEach(function (coordenadas, index) {
    // Extrae latitud y longitud
    var [lat, lng] = coordenadas.split(",").map(parseFloat);
    
    // Crea un marcador para cada coordenada
    var marker = L.marker([lat, lng], {icon: heladeraIcon}).addTo(map);
    
    // Asocia el popup personalizado al marcador
    marker.bindPopup(popups[coordenadas]);
    
    // Crea un nuevo ítem de lista para cada ubicación
    var li = document.createElement('li');
    li.className = 'list-group-item d-flex justify-content-between align-items-center';
    li.textContent = popups[coordenadas];
    
    // Crea un div para los botones de modificar y eliminar
    var buttonDiv = document.createElement('div');
    buttonDiv.className = 'btn-group btn-group-sm';
    
    // Crea el botón de modificar
    var modifyBtn = document.createElement('button');
    modifyBtn.className = 'btn btn-link';
    modifyBtn.innerHTML = '<img src="/img/modificar.png" alt="Modificar" width="20">';
    modifyBtn.setAttribute('title', 'Modificar');
    modifyBtn.onclick = function() {
        document.getElementById('nameInput').value = popups[coordenadas];
        document.getElementById('itemIndex').value = index;
        new bootstrap.Modal(document.getElementById('modifyModal')).show();
    };
    
    // Crea el botón de eliminar
    var deleteBtn = document.createElement('button');
    deleteBtn.className = 'btn btn-link';
    deleteBtn.innerHTML = '<img src="/img/eliminar.png" alt="Eliminar" width="20">';
    deleteBtn.setAttribute('title', 'Eliminar');
    deleteBtn.onclick = function() {
        document.getElementById('itemIndex').value = index;
        new bootstrap.Modal(document.getElementById('deleteModal')).show();
    };

    // Agrega los botones al div
    buttonDiv.appendChild(modifyBtn);
    buttonDiv.appendChild(deleteBtn);
    
    // Agrega el div con los botones al ítem de lista
    li.appendChild(buttonDiv);

    // Agrega el ítem de lista al UL
    heladeraList.appendChild(li);
});
*/
var map = L.map('map').setView([-34.55012980593437, -58.36869394495356], 7);

// Crea una capa de mapa de tipo TileLayer
L.tileLayer('https://api.maptiler.com/maps/basic-v2/{z}/{x}/{y}@2x.png?key=IVQEtcjkvUuNTleGu82d', {
    attribution: '<a href="https://www.maptiler.com/copyright/" target="_blank">&copy; MapTiler</a> <a href="https://www.openstreetmap.org/copyright" target="_blank">&copy; OpenStreetMap contributors</a>'
}).addTo(map);

// Icono personalizado para las heladeras (puedes cambiar el tamaño o la imagen según lo desees)
var heladeraIcon = L.icon({
    iconUrl: '/img/marcadorHeladera.png',
    iconSize: [35, 50],
    iconAnchor: [30, 50],
});

var heladeraDesactivaIcon = L.icon({
    iconUrl: '/img/marcadorHeladeraDesactiva.png',
    iconSize: [35, 50],
    iconAnchor: [30, 50],
});

function agregarMarcador(nombre, latitud, longitud, direccion){
    console.log("Nombre: ", nombre);
    var marker = L.marker([latitud, longitud], {icon: heladeraIcon}).addTo(map);
    marker.bindPopup(`Heladera ${nombre} ubicada en ${direccion || 'una dirección desconocida'}`);
}

function agregarMarcadorDesactiva(nombre, latitud, longitud, direccion){
    console.log("Nombre: ", nombre);
    var marker = L.marker([latitud, longitud], {icon: heladeraDesactivaIcon}).addTo(map);
    marker.bindPopup(`Heladera ${nombre} ubicada en ${direccion || 'una dirección desconocida'}. Se encuentra desactivada`);
}