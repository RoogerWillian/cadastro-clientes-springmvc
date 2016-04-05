$('#btnAddTelefone').on('click', function() {
	var form = $(this).parents('form:first');
	var action = form.attr('action');
	form.attr('action',action + '/addTelefone');
});

$('#btnAddEndereco').on('click', function() {
	var form = $(this).parents('form:first');
	var action = form.attr('action');
	form.attr('action',action + '/addEndereco');
});
