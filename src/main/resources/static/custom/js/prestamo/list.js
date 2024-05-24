var librosPrestados = [];
var prestamoSelectId = undefined;

function retornarLibros(prestamoId) {
	prestamoSelectId = prestamoId;
	librosPrestados = [];
	$('#prestamo_'+prestamoId).find('li').each(function (i){
		var data = JSON.parse($(this).attr('data-libro'))
		if(data.estado == 2){
			librosPrestados.push( data );
		}

		/*var libro = {id: $(this).attr('data-id'), title: $(this).text(), data:$(this).attr('data-libro')}//recoge el idUnidad y tiutlo del li
		librosPrestados.push( libro );
	*/});
	console.log(librosPrestados);
	cargarListaDeLibrosPrestados();
	$('#select-all').prop('checked', false);
	$('.returnBookChk').prop('checked', false);
	$('.return-book-modal').modal('show');
	
}

function cargarListaDeLibrosPrestados() {

	var trs = '';
	trs += '<tr>';
	trs += '<td style="font-weight: 700; width: 70px">#Id:'+prestamoSelectId+'</td>';
	trs += '<td style="font-weight: 700">Título del libro</td>';
	trs += '<td style="font-weight: 700">ISBN</td>';
	trs += '<td style="font-weight: 700">Ubicación</td>';
	trs += '<td style="font-weight: 700">Cod Unidad</td>';
	trs += '</tr>';

	for(var i=0 ; i<librosPrestados.length ; i++) {
		trs += '<tr>';
		trs += '<td><input type="checkbox" class="libroSelec" value="'+librosPrestados[i].id+'" onclick="retornarLibroSelec()"></td>';
		trs += '<td>'+librosPrestados[i].libro.titulo+'</td>';
		trs += '<td>'+librosPrestados[i].isbn+'</td>';
		trs += '<td>'+librosPrestados[i].ubicacion+'</td>';
		trs += '<td>'+librosPrestados[i].id+'</td>';
		trs += '</tr>';


	}
	$('#table-retornar-libros tr:gt(0)').remove(); //tr:gt(0) significa que elimina todas las filas menos la primera
	$('#table-retornar-libros').append(trs);

}

function retornarLibroSelec() {
	var total = $('.libroSelec').length;
	var checkedCantidad = $('.libroSelec:checkbox:checked').length;
	if( total == checkedCantidad ) {
		$('#select-all').prop('checked', true);
	} else {
		$('#select-all').prop('checked', false);
	}
}

function seleccionarTodos() {
	if( $('#select-all').prop('checked') ) {
		$('.libroSelec').prop('checked', true);
	} else {
		$('.libroSelec').prop('checked', false);
	}
}

function confirmarRetornoDeLibro() {
	var checkedCantidad = jQuery('.libroSelec:checkbox:checked').length;

	if( checkedCantidad > 0 ) {
		var ids = [];
		$('.libroSelec:checkbox:checked').each(function (i){
			ids.push( $(this).val() );
		});
		console.log(ids)
		$.get( '/rest/prestamo/'+prestamoSelectId, {ids: ids.join(',')} ).done(function (msg){
			if( msg=='completado' ) {
				window.location = '/prestamo/listar/enCurso';
			}
		});
	}
}

function viewPrestamo(prestamo){
	let prestamoId = $(prestamo).attr('data-prestamo-id');
	let tabla = '';
	$.get( '/rest/prestamo/ver/'+prestamoId ).done(function (data){
		console.log(data);
		//poner id en el titulo
		$('#viewPrestamoModal #prestamo-id').text('(Id#'+prestamoId+')');
		tabla += '<div class="row">';
		tabla += '<div class="col-12 m-10" id="contenido">';
		tabla += '<div class="form-group row fila">';
		tabla += '<label class="col-sm-4 col-form-label">Fecha Préstamo * :</label>';
		tabla += '<div class="col-sm-5">';
		tabla += '<input type="text" class="form-control" id="book-autor" readonly value="'+data.fechaPrestamo.split('T')[0]+'" >';
		tabla += '</div>';
		tabla += '</div>';
		tabla += '<div class="form-group row fila">';
		tabla += '<label class="col-sm-4 col-form-label">Fecha Devolución * :</label>';
		tabla += '<div class="col-sm-5">';
		tabla += '<input type="text" class="form-control" id="book-autor" readonly value="'+data.fechaRetorno.split('T')[0]+'" >';
		tabla += '</div>';
		tabla += '</div>';
		tabla += '<div class="form-group row fila">';
		tabla += '<label class="col-sm-4 col-form-label">Estado * :</label>';
		tabla += '<div class="col-sm-5">';
		let estado = data.estado==1 ? 'Retornado' : 'En Curso';
		tabla += '<input type="text" class="form-control" id="book-autor" readonly value="'+estado+'" >';
		tabla += '</div>';
		tabla += '</div>';
		tabla += '</div>';
		tabla += '</div>';



		tabla += '<table class="table">';
		tabla += '<thead>';
		tabla += '<tr>';
		tabla += '<th style="padding-bottom: 10px; padding-left: 0px">Unidades: </th>';
		tabla += '</tr>';
		tabla += '</thead>';
		tabla += '<tbody>';
		tabla += '<tr>';
		tabla += '<td style="font-weight: 700; width: 70px">#UnitId</td>';
		tabla += '<td style="font-weight: 700">Libro</td>';
		tabla += '<td style="font-weight: 700">ISBN</td>';
		tabla += '<td style="font-weight: 700">Ubicación</td>';
		tabla += '<td style="font-weight: 700">Retorno</td>';
		tabla += '<td style="font-weight: 700">Estado</td>';
		tabla += '</tr>';
		data.librosPrestados.forEach(function (libro){
			tabla += '<tr>';
			tabla += '<td>'+libro.unidadId+'</td>';
			if(libro.estado == 1){
				tabla += '<td style="background-color:#d4edda">'+libro.titulo+'</td>';
			}else{
				tabla += '<td style="background-color:#f8d7da">'+libro.titulo+'</td>';
			}
			tabla += '<td>'+libro.isbn+'</td>';
			tabla += '<td>'+libro.ubicacion+'</td>';
			//mostrar solo la fecha
			libro.fechaRetorno = libro.fechaRetorno.split('T')[0];
			tabla += '<td>'+libro.fechaRetorno+'</td>';
			tabla += '<td>'+libro.estado+'</td>';
			tabla += '</tr>';
		});
		tabla += '</tbody>';
		tabla += '</table>';



		tabla += '<table class="table">';
		tabla += '<thead>';
		tabla += '<tr>';
		tabla += '<th style="padding-bottom: 10px; padding-left: 0px">Miembro: </th>';
		tabla += '</tr>';
		tabla += '</thead>';
		tabla += '<tbody>';
		tabla += '<tr>';
		tabla += '<td style="font-weight: 700">Nombre</td>';
		tabla += '<td style="font-weight: 700">Genero</td>';
		tabla += '<td style="font-weight: 700">DNI</td>';
		tabla += '<td style="font-weight: 700">Contacto</td>';
		tabla += '<td style="font-weight: 700">Edad</td>';
		tabla += '</tr>';
		tabla += '<tr>';
		tabla += '<td>'+data.miembro.nombre+'</td>';
		tabla += '<td>'+data.miembro.genero+'</td>';
		tabla += '<td>'+data.miembro.dni+'</td>';
		tabla += '<td>'+data.miembro.contacto+'</td>';
		tabla += '<td>'+data.miembro.edad+'</td>';
		tabla += '</tr>';
		tabla += '</tbody>';
		tabla += '</table>';



		$('#viewPrestamoModal #datos').html(tabla);
		/*
		* <table class="table">
                                <thead>
                                <tr>
                                    <th style="padding-bottom: 10px; padding-left: 0px">Unidades:</th>
                                </tr>
                                </thead>
                            </table>
                            <table class="table">
                                <thead>
                                <tr>
                                    <th style="padding-bottom: 10px; padding-left: 0px">Miembro:</th>
                                </tr>
                                </thead>
                            </table>
                            <div class="row">
                                <div class="col-12 m-10" id="contenido">
                                    <div class="form-group row fila">
                                        <label class="col-sm-4 col-form-label">Autor * :</label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" id="book-autor" readonly>
                                        </div>
                                    </div>
                                </div>
                            </div>*/
		/*$('#viewPrestamoModal .modal-body').html(msg);
		$('#viewPrestamoModal').modal('show');*/
	});
}
