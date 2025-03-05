document.getElementById('registroJuaridicaForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    let formData = new FormData(this);
    await registrar('/registroPersonaJuridica', formData);

});