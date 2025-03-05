$(document).ready(function() {

    $('#btnDespacharTarjeta').click(function() {
        const tarjetaID = $(this).data('id');
        $.ajax({
            url: 'despacharTarjeta/' + tarjetaID,
            method: 'POST',
            success: function() {
                $('#itemTarjeta'+ tarjetaID).remove();
            }
        });
    });

});

document.addEventListener('DOMContentLoaded', () => {
    const eliminarTecnicoModal = document.getElementById('eliminarTecnicoModal');
    const eliminarTecnicoIdInput = document.getElementById('eliminarTecnicoId');

    eliminarTecnicoModal.addEventListener('show.bs.modal', (event) => {
        const button = event.relatedTarget;
        const tecnicoId = button.getAttribute('data-id');
        eliminarTecnicoIdInput.value = tecnicoId;
    });
});
