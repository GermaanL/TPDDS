document.getElementById('registroForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    let formData = new FormData(this);
    await registrar('/registro', formData);

});

