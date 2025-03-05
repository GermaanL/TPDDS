        // Crea un mapa centrado en la coordenada -34.55012980593437,-58.36869394495356 con un zoom de 7
        var map = L.map('map').setView([-34.55012980593437, -58.36869394495356], 7);

        // Crea una capa de mapa de tipo TileLayer
        L.tileLayer('https://api.maptiler.com/maps/basic-v2/{z}/{x}/{y}@2x.png?key=IVQEtcjkvUuNTleGu82d', {
            attribution: '<a href="https://www.maptiler.com/copyright/" target="_blank">&copy; MapTiler</a> <a href="https://www.openstreetmap.org/copyright" target="_blank">&copy; OpenStreetMap contributors</a>'
        }).addTo(map); // Agrega la capa de mapa al mapa


        var heladeraIcon = L.icon({
            iconUrl: 'marcadorHeladera.png',
            iconSize:     [35, 50], 
            iconAnchor:   [30, 50], 
        });

        // Objeto que mapea coordenadas a contenido de popup
        var popups = {
            "-34.65942907369565,-58.467921958535946": "UTN campus",
            "-34.59844621249422,-58.42008481621035": "UTN medrano",
            "-34.56593800505455,-58.606189218064046": "Kiosco Menes",
            "-34.62811586361482,-58.44650128737324": "UBA Filosofia",
            "-34.6035622548135, -58.38160258922659": "Obelisco"
        };
        
        // Itera sobre las coordenadas y agrega un marcador en cada una
        Object.keys(popups).forEach(function (coordenadas) {
            // Extrae latitud y longitud
            var [lat, lng] = coordenadas.split(",").map(parseFloat);
            
            // Crea un marcador para cada coordenada
            var marker = L.marker([lat, lng], {icon: heladeraIcon}).addTo(map);
            
            // Asocia el popup personalizado al marcador
            marker.bindPopup(popups[coordenadas]);
        });

