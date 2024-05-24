var libroId, libroNombre, tipo;
modalRemove = $('.remove-libro-modal');
function eliminarLibroDialog(el) {
	$('#viewBooksModal').modal('hide');
	tipo = $(el).attr('data-tipo');

	if(tipo == 'unidad') {
		unidadId = $(el).attr('data-unidad-id');
		modalRemove.find('.modal-title').text('Eliminar unidad');
		//add <p></p> to the modal body
		modalRemove.find('.modal-body').html('<p>¿Está seguro de eliminar la unidad <strong>' + unidadId + '</strong>?</p>');
		modalRemove.find('.modal-footer').append('<button ype="button" class="btn btn-danger" onclick="eliminarUnidad(unidadId)">Eliminar</button>');
	}
	if (tipo == 'libro') {
		libroId = $(el).attr('data-libro-id');
		libroNombre = $(el).attr('data-libro-name');
		modalRemove.find('.modal-title').text('Eliminar libro');
		//add <p></p> to the modal body
		modalRemove.find('.modal-body').html('<p>¿Está seguro de eliminar el libro <strong>' + libroNombre + '</strong>?</p>');
		modalRemove.find('.modal-footer').append('<button type="button" class="btn btn-danger" onclick="eliminarLibro(libroId)">Eliminar</button>');
	}

}
function resetRemoveModal(){
	modalRemove.find('.modal-footer').find('button').last().remove();
	modalRemove.find('.modal-title').text('');
	modalRemove.find('.modal-body').empty();
}
function eliminarUnidad(id) {
	modalRemove.modal('hide');
	console.log('eliminar unidad ' + id);

	window.location = "/libroUnidad/eliminar/" + id;
}
function eliminarLibro(id) {
	modalRemove.modal('hide');
	console.log('eliminar libro ' + id);

	window.location = "/libro/cambiar/" + id;

}

function viewBook(el){
	libroId = $(el).attr('data-book-id');
	$.get('/rest/libro/buscar/'+ libroId, function(data) {
		console.log(data);

		$('#viewBookModal').find('#book-tag').val(data.tag);
		$('#viewBookModal').find('#book-autor').val(data.autor);
		$('#viewBookModal').find('#book-editorial').val(data.editorial);
		$('#viewBookModal').find('#book-isbn').val(data.isbn);
		$('#viewBookModal').find('#book-estado').val(data.estado);
		$('#viewBookModal').find('#book-fechaPublicacion').val(data.fechaPublicado);
		$('#viewBookModal').find('#book-stockTotal').val(data.stockTotal);
		$('#viewBookModal').find('#book-stockDisponible').val(data.stockDisponible);
		$('#viewBookModal').find('#book-stockPrestado').val(data.stockPrestado);
		//recoore el array de categorias
		var categorias = '';
		$.each(data.categorias, function(i, categoria){
			categorias += categoria.nombre + ', ';
		});
		$('#viewBookModal').find('#book-categorias').val(categorias);
	});
}

function viewAllBooks(el) {
	//debe limpiar el contenido de la tabla no los headres
	$('#viewBooksModal').find('#cuerpo-unidades').empty();
	libroIsbn = $(el).attr('data-unit-isbn');
	$.get('/rest/libro/listarUnidades/'+ libroIsbn, function(data) {
		if( data ) {
			console.log(JSON.stringify(data));
			data.forEach(function(unidad){
				$('#viewBooksModal').find('#tablaDeUnidades').append(
					'<tr>' +
					'<th scope="row">'+ unidad.id +'</th>' +
					'<td>'+ unidad.isbn +'</td>' +
					'<td>'+ unidad.estado +'</td>' +
					'<td>'+ unidad.fechaIngreso.slice(0,10) +'</td>' +
					'<td>'+ unidad.ubicacion +'</td>' +
					'<td>'+ '<span style="font-size: 10px; font-weight: bolder">'+unidad.nota+'</span>' +'</td>' +
					'<td>'+ '<a href="/libroUnidad/editar/' +unidad.id +'"><i class="fa fa-edit"></i></a>' +
					'<a data-toggle="modal" data-target=".remove-libro-modal" onclick="eliminarLibroDialog(this);"' +
					'data-unidad-id="'+ unidad.id +'" data-tipo="unidad" style="cursor: pointer;">\n' +
					'<i class="fa fa-remove"></i>\n' + '</a>' + '</td>' +
					'</tr>'
				);
			});
		}
	});


}


/*
*
* 								<tr>
                                    <th scope="row">1</th>
                                    <td>Mark</td>
                                    <td>Otto</td>
                                    <td>@mdo</td>
                                </tr>
                                <tr>
                                    <th scope="row">2</th>
                                    <td>Jacob</td>
                                    <td>Thornton</td>
                                    <td>@fat</td>
                                </tr>
                                <tr>
                                    <th scope="row">3</th>
                                    <td>Larry</td>
                                    <td>the Bird</td>
                                    <td>@twitter</td>
                                </tr>*/