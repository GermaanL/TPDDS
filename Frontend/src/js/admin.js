// admin.js
import { obtenerHeladeras } from './services/heladeras.js';
import { crearMapa } from './map.js';

document.addEventListener('DOMContentLoaded', () => {
    obtenerHeladeras().then(datos => {
        console.log("Datos recibidos:", datos);
        crearMapa(datos, "marcadorHeladera.png");
        generarListaHeladeras(datos);
    }).catch(error => {
        console.error("Error al obtener las heladeras:", error);
    });
});

function generarListaHeladeras(datos) {
    var heladeraList = document.getElementById('heladeraList');
    if (!heladeraList) {
        console.error("Elemento heladeraList no encontrado");
        return;
    }

    heladeraList.innerHTML = ''; // Limpia la lista antes de agregar nuevos ítems
    console.log("Generando lista...");

    datos.forEach((heladera, index) => {
        console.log("Agregando heladera a la lista:", heladera.name);
        var li = document.createElement('li');
        li.className = 'list-group-item d-flex justify-content-between align-items-center';
        li.textContent = heladera.name;
        
        // Crea un div para los botones de modificar y eliminar
        var buttonDiv = document.createElement('div');
        buttonDiv.className = 'btn-group btn-group-sm';
        
        // Crea el botón de modificar
        var modifyBtn = document.createElement('button');
        modifyBtn.className = 'btn btn-link';
        modifyBtn.innerHTML = '<img src="src/img/modificar.png" alt="Modificar" width="20">';
        modifyBtn.setAttribute('title', 'Modificar');
        modifyBtn.onclick = function() {
            document.getElementById('nameInput').value = heladera.name;
            document.getElementById('itemIndex').value = index;
            new bootstrap.Modal(document.getElementById('modifyModal')).show();
        };
        
        // Crea el botón de eliminar
        var deleteBtn = document.createElement('button');
        deleteBtn.className = 'btn btn-link';
        deleteBtn.innerHTML = '<img src="src/img/eliminar.png" alt="Eliminar" width="20">';
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
}
