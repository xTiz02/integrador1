var categoriaId, categoriaNombre;

function eliminarCategoriaDialog(el) {
	categoriaId = $(el).attr('data-categoria-id');//attr es para obtener el valor de un atributo de un elemento
	categoriaNombre = $(el).attr('data-categoria-name');
	$('.remove-categoria-modal').find('#categoria-name').text(categoriaNombre);
}

function eliminarCategoria() {
	$('.remove-categoria-modal').modal('hide');
	window.location = "/categoria/eliminar/" + categoriaId;
}