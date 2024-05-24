$(document).ready(function() {
	function traerMiembros(tipo) {//2
		$.get('/rest/miembro/listar/'+ tipo, function(data) {
			if( data ) {
				llenarMiembros(data);

			}
		});
	}
	function traerLibros(idCat) {//2
		$.get('/rest/libro/listar/' + idCat, function(data) {
			if( data ) {
				llenarLibros( data );
			}
		});
	}

	function traerUnidades(isbn){
		$.get('/rest/libroUnidad/listar/' + isbn, function(data) {
			if( data ) {
				llenarUnidades( data );
			}
		});
	}
	function llenarUnidades(lista){
		$('#unidadSelec').empty().append('<option selected="selected" value="">-- Seleccione una unidad (Id/Zona) --</option>');
		$.each(lista, function(k, v) {
			$('#unidadSelec').append($("<option></option>")
				.attr("value",v.id)
				.attr("data-ubicacion", v.ubicacion).text(v.id+ '/'+v.ubicacion))
		});
	}

	$('#libroSelec').on('change', function(){
		let isbn = $(this).find('option:selected').attr('data-isbn');
		console.log(isbn);
		if( isbn ) {
			traerUnidades(isbn);
		} else {
			llenarUnidades( [] );
		}
	} );


	function llenarMiembros( lista ) {//llena la lista(option) de miembros 3

		$('#miembroSelec').empty().append('<option selected="selected" value="">-- Seleccione un miembro --</option>');//vacia la lista
		$.each(lista, function(k, v) {
			$('#miembroSelec').append($("<option></option>")
				.attr("value",v.id).text(v.primerNombre + ' ' + v.segundoNombre + ' ' + v.apellido ));
		});

	}

	$('#tipoMiembro').on('change', function() {//accion final llena la lista de miembros dependiendo del tipo 1
		let tipo = $(this).val();

		if( tipo ) {
			traerMiembros( tipo );
		} else {
			llenarMiembros( [] );
		}
	});




	function llenarLibros( lista ) {//3
		$('#libroSelec').empty().append('<option selected="selected" value="">-- Seleccionar libro --</option>');
		$.each(lista, function(k, v) {
		     $('#libroSelec').append($("<option></option>")
		                    .attr("value",v.id).text(v.titulo)
				 			.attr("data-isbn", v.isbn)
		                    .attr("data-autor", v.autor)
		                    .attr("data-tag", v.tag)
		                    .attr("data-editorial", v.editorial));
		});
	}

	$('#categoriaSelec').on('change', function(){//1
		let id = $(this).val();
		if( id ) {
			traerLibros( id );
		} else {
			llenarLibros( [] );

		}
		llenarUnidades( [] );
	});




	var listaLibros = [];
	let rowNum = 1;

	$('#addLibroBtn').on('click', function() {//1
		let libroSelec = $('#libroSelec option:selected');
		let unidadSelec = $('#unidadSelec option:selected');
		let id = libroSelec.val();
		let titulo = libroSelec.text();
		let tag = libroSelec.attr("data-tag");
		let autor = libroSelec.attr("data-autor");
		let editorial = libroSelec.attr("data-editorial");
		let ubicacion = unidadSelec.attr("data-ubicacion");
		console.log("ubicaion: "+ubicacion);
		let idUnidad = unidadSelec.val();
		console.log("id: "+idUnidad);
		if( id && !libroEnLaTabla(id,idUnidad) && titulo && tag && autor && editorial && idUnidad && ubicacion) {
			let libro = { id: id, titulo: titulo, unidad: idUnidad, autor: autor,ubicacion: ubicacion };
			listaLibros.push(libro);
			ponerLibroEnLaTabla(libro);
			$('#libroSelec').val('');

		}
	});

	function libroEnLaTabla(idLibro, idUnidad) {//2
		for(var i=0 ; i<listaLibros.length ; i++) {
			if( listaLibros[i].id === idLibro && listaLibros[i].unidad === idUnidad) {
				return true;
			}
		}
		return false;
	}


	function ponerLibroEnLaTabla(libro) {//3
		var fecha = $('#fechaRetorno').val();
		var trs = '';
		trs += '<tr>';
		trs += '<td>'+libro.unidad+'</td>';
		trs += '<td>'+libro.titulo+'</td>';
		trs += '<td>'+libro.autor+'</td>';
		trs += '<td>'+libro.ubicacion+'</td>';
		trs += '<td> <input type="date" class="form-control" placeholder="0/0/0" id="libroFechaRetorno-'+libro.unidad+'">'+fecha+'</td>';
		trs += '<td><a href="javascript:void(0)" data-unidad="'+libro.unidad+'" > <i class="fa fa-remove"></i></a></td>';//se guarda el id del libro en la etiqueta a
		trs += '</tr>';
		//"javascript:void(0) es para que no recargue la pagina"

		$('#tablaDeLibros tr:last').after( trs );//agrega la fila despues de la ultima fila
		rowNum++;
	}

	$('#tablaDeLibros').on('click', 'a', function() {//1
		let trPadre  = $(this).closest('tr');
		let id = $(this).data('unidad');//obtiene la unidad de la etiqueta a
		
		trPadre.remove();
		eliminarDeLaLista(id);

	});

	function eliminarDeLaLista(unidad) {//2
		for( let i=0 ; i<listaLibros.length ; i++ ) {
			if( listaLibros[i].unidad == unidad ) {
				listaLibros.splice(i, 1);
				break;
			}
		}
	}







	$('#prestamosBtn').on('click', function(){//1
		console.log(listaLibros);
		let errores = listaErrores();
		//imprimir lista de errores
		 console.log(errores);

		if( errores.length > 0 ) {
			$('.errores-modal').find('.modal-body').html( errores.join('<br/>') );
			$('.errores-modal').modal('show');
		} else {
			var prestamo = traerDataDeLosLibros();
			var miembro = $('#miembroSelec').val();
			var fecha = $('#fechaRetorno').val();
			//imprimir prestamo
			console.log(prestamo);
			//agregar contetType y dataType
			$.ajax({
				url: '/rest/prestamo/guardar',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					libros: prestamo,
					miembro: miembro,
					fechaRetorno: fecha
				}),
				success: function(data) {
					if( data ) {
						console.log(data);
						window.location.href = '/prestamo/listar/enCurso';
					}
				}
			});
		}
	});

	function traerDataDeLosLibros() {
		let ids = {};
		for(let i=0 ; i<listaLibros.length ; i++) {
			//agregar id y sufecha de retorno
			ids[listaLibros[i].unidad] = $('#libroFechaRetorno-'+listaLibros[i].unidad).val();
		}
		return ids;
	}

	function listaErrores() {
		let errores = [];
		let miembro = $('#miembroSelec option:selected').val();
		let fechaGeberal = $('#fechaRetorno').val();
		if (!fechaGeberal){
			errores.push('- Escriba una fecha de retorno general');
		}else {
			if (new Date(fechaGeberal) < new Date()){
				errores.push('- Escriba una fecha valida de retorno general');
			}
		}
		for (let i=0 ; i<listaLibros.length ; i++) {
			let unidad = listaLibros[i].unidad;
			let fecha = $('#libroFechaRetorno-'+unidad).val();
			if (!fecha){
				errores.push('- Escriba una fecha de retorno para el libro '+listaLibros[i].titulo);
			}else {
				if ( (new Date(fecha)) >= new Date(fechaGeberal)){
					errores.push('- Escriba una fecha de retorno diferente para el libro '+listaLibros[i].titulo);
				}
				if (new Date(fecha) < new Date()){
					errores.push('- Escriba una fecha valida para el libro '+listaLibros[i].titulo);
				}
			}

		}
		if( !miembro ) {
			errores.push('- Selecciona un miembro');
		}
		if( listaLibros.length == 0 ) {
			errores.push('- Agrega al menos un libro');
		}
		return errores;
	}


});

