function mostrarMensajeError(mensaje) {
    alert(mensaje);
    //TODO hacer un carte de mensaje mejor
}

function mostrarMensaje(mensaje) {
    alert(mensaje);
    //TODO hacer un carte de mensaje mejor
}

async function registrar(url, formData) {
    const response = await fetch(url, {
        method: 'POST',
        body: formData,
        headers: {
            'Accept': 'application/json'
        }
    });

    if (!response.ok) {
        const data = await response.json();
        mostrarMensajeError(data.respuesta); //los mensajes de error los mandamos desde el back y en fron solo los mostramos
    } else {
        window.location.href = '/login';
    }
}

function saludar() {
    console.log("¡Hola desde archivo 1!");
}
