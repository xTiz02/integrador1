
//poner la fecha actual en el input de fecha de retorno en formato yyyy/mm/dd
if( !$('#fechaIngreso').val() ) {
    $('#fechaIngreso').val(new Date().toISOString().slice(0,10));
}

$('#gotoListBtn').on('click', function() {
    window.location = "/libro/listar";
});