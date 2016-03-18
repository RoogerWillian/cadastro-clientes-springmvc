$(function() {
	$('[rel="tooltip"]').tooltip();
	$('#campoTelefone').mask('(99) 9999?9-9999');
	$('#campoCpf').mask('999.999.999-99');
	$('#campoData').mask('99/99/9999');
	$('#campoCep').mask('99999999');
});

$('#confirmacaoExclusaoModal').on(
		'show.bs.modal',
		function(event) {
			var button = $(event.relatedTarget);

			var idCliente = button.data('id');
			var nomeCliente = button.data('nome');

			var modal = $(this);
			var form = modal.find('form');
			var action = form.data('url-base');
			if (!action.endsWith('/')) {
				action += '/';
			}
			form.attr('action', action + idCliente);

			/* Colocando a descricao do titulo no modal */
			modal.find('.modal-body span').html(
					'Tem certeza que deseja excluir o cliente <strong>'
							+ nomeCliente + '</strong>?');
		});

$('#telefones').on(
		'show.bs.modal',
		function(e) {
			var button = $(e.relatedTarget);
			var url = button.data('url');
			var nomeCliente = button.data('cliente');
			
			var modal = $(this);
			modal.find('.modal-title').html(
					'Telefones' + ' ' + '|' + ' <strong>' + nomeCliente + ' </strong>');
});

$('#enderecos').on(
		'show.bs.modal',
		function(e) {
			var button = $(e.relatedTarget);
			var url = button.data('url');
			var nomeCliente = button.data('cliente');
			
			var modal = $(this);
			modal.find('.modal-title').html(
					'Endereços' + ' ' + '|' + ' <strong>' + nomeCliente + ' </strong>');
});
