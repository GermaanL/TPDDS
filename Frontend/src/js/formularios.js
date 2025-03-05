import { obtenerHeladeras } from "./services/heladeras.js";

document.addEventListener('DOMContentLoaded', async () => {
    const heladeraSelect = document.getElementById('heladerasSelect');
    const selectHeladeraOrigen = document.getElementById('inputHeladeraOrigen');
    const selectHeladeraDestino = document.getElementById('inputHeladeraDestino');
    
    
    if (heladeraSelect || selectHeladeraOrigen || selectHeladeraDestino) {  // Verifica si el elemento existe
        try {
            const heladeras = await obtenerHeladeras();

            poblarSelectHeladeras(selectHeladeraOrigen, heladeras);
            poblarSelectHeladeras(selectHeladeraDestino, heladeras);
            poblarSelectHeladeras(heladeraSelect, heladeras);

            selectHeladeraOrigen.addEventListener('change', () => actualizarOpciones(selectHeladeraDestino, selectHeladeraOrigen.value));
            selectHeladeraDestino.addEventListener('change', () => actualizarOpciones(selectHeladeraOrigen, selectHeladeraDestino.value));

        } catch (error) {
            console.error('Error al llenar el select de heladeras:', error);
        }
    }
});

function poblarSelectHeladeras(selectElement, heladeras) {
    if (selectElement) {
        heladeras.forEach(heladera => {
            const option = document.createElement('option');
            option.value = heladera.id;
            option.textContent = heladera.name;
            selectElement.appendChild(option);
        });
    }
}

function actualizarOpciones(selectElement, valorSeleccionado) {
    const opciones = selectElement.querySelectorAll('option');
    opciones.forEach(option => {
        if (option.value === valorSeleccionado) {
            option.disabled = true;  // Deshabilitar la opción seleccionada
        } else {
            option.disabled = false; // Habilitar las demás opciones
        }
    });
}