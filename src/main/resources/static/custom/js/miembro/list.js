var memberId, memberName;

function removeMemberDialog(el) {
	memberId = $(el).attr('data-member-id');
	memberName = $(el).attr('data-member-name');
	$('.remove-member-modal').find('#member-name').text(memberName);
}

function removeMember() {
	$('.remove-member-modal').modal('hide');
	window.location = "/miembro/eliminar/" + memberId;
}

function viewMember(el){
	memberId = $(el).attr('data-member-id');
	$.get('/rest/miembro/buscar/'+ memberId, function(data) {
		console.log(typeof data.fechaNac);
		//insert data in input

		$('#viewMemberModal').find('#member-first-name').val(data.primerNombre);
		$('#viewMemberModal').find('#member-second-name').val(data.segundoNombre);
		$('#viewMemberModal').find('#member-apellido').val(data.apellido);
		$('#viewMemberModal').find('#member-dni').val(data.dni);
		$('#viewMemberModal').find('#member-email').val(data.email);
		$('#viewMemberModal').find('#member-contacto').val(data.contacto);

			$('#viewMemberModal').find('#member-fechNac').val(data.fechaNac);

			$('#viewMemberModal').find('#member-fechaIngreso').val(data.fechaIngreso);


		$('#viewMemberModal').find('#member-tipo').val(data.tipo);
		$('#viewMemberModal').find('#member-genero').val(data.genero);
	});
}

