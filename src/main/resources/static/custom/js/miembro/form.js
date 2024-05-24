$(document).ready(function() {
	$('#saveBtn').on('click', function() {
		if (isValidateBirthDate()) {
			$('#member-form').submit();
		} else {
			$('#dobErr').text('Fecha Invalida');
		}
	});

	function isValidateBirthDate() {
		var dateStr = $('#dateOfBirth').val();
		var timestamp = Date.parse(dateStr)
		return !isNaN(timestamp)
	}

	$('#gotoListBtn').on('click', function() {
		window.location = "/miembro/listar";
	});
});