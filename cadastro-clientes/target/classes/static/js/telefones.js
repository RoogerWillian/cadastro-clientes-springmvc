$('#confirmacaoExclusaoTelefone').on(
		'show.bs.modal',
		function(event) {
			var button = $(event.relatedTarget);

			var idEndereco = button.data('id');
			var nomeCliente = button.data('cliente');
			var telefone = button.data('telefone');
			
			var modal = $(this);
			var form = modal.find('form');
			var action = form.data('url-base');
			if (!action.endsWith('/')) {
				action += '/';
			}
			form.attr('action', action + idEndereco);

			/* Colocando a descricao do titulo no modal */
			modal.find('.modal-body span').html(
					'Tem certeza que deseja excluir o telefone <strong>'
							+ telefone + '</strong> do cliente <strong>' + nomeCliente +  '</strong> ?');
});
