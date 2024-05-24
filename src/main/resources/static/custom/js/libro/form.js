
$(document).ready(function() {
	var fulltag;


	$('#gotoListBtn').on('click', function() {
		window.location = "/libro/listar";
	});
	
	$('#category-selectbox').on('change', function() {
		updateTagField();
	});
	
	$('#resetBtn').on('click', function() {
		setTimeout(function(){
			updateTagField();
		}, 10);
		
	});
	
	function updateTagField() {
		var shortName =  $("#category-selectbox option:selected").attr("short-name");
		if( shortName ) {
			$('#tag').val( shortName + '-' );
		}
	}
	
	if( !$('#id').val() ) {
		updateTagField();
	}
});