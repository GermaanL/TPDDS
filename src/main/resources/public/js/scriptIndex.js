async function solicitarTarjeta() {
    const response = await fetch("/solicitarTarjeta", {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    });
    const data = await response.json();
    if (!response.ok) {
        mostrarMensajeError(data.respuesta); //los mensajes de error los mandamos desde el back y en fron solo los mostramos
    }
    else {
        mostrarMensaje(data.respuesta);
        location.reload();
    }
}

//Cuando se manda el formulario de autorizar tarjeta
document.getElementById('autorizarTarjetaForm').addEventListener('submit',  function(event) {
    event.preventDefault();
    let formData = new FormData(this);
    const response =  fetch("/autorizarTarjeta", {
        method: 'POST',
        body: formData,
        headers: {
            'Accept': 'application/json'
        }
    }).then(response => {
        if (!response.ok) {
            const data = response.json();
            mostrarMensajeError(data.respuesta); //los mensajes de error los mandamos desde el back y en fron solo los mostramos
        } else {
            window.location.href = '/main';
        }
    });

});
