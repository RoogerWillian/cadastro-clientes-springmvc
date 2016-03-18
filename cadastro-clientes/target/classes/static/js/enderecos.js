$('#campoCep').on('blur', function() {
	var inpCep = $(this);
	var cep = inpCep.val();

	if (cep != '') {

		$.ajax({
			url : 'https://viacep.com.br/ws/' + cep + '/json/',
			dataType : 'jsonp',
			crossDomain : true,
			method : 'GET',
			beforeSend : function(){
				$.notify(
					{
						icon: 'glyphicon glyphicon-warning-sign',
						message: 'Buscando CEP: ' + '<strong>' + cep + '</strong>'
					}
				)
			},
			success : function(data) {
				if (!data.erro) {				
					$('#logradouro').val(data.logradouro);
					$('#bairro').val(data.bairro);
					$('#cidade').val(data.localidade);
					$('#estado').val(data.uf);
				}
			},
			error : function(msg) {
				console.log(msg);
			}
		});
	}
});

$('#confirmacaoExclusaoEndereco').on(
		'show.bs.modal',
		function(event) {
			var button = $(event.relatedTarget);

			var idEndereco = button.data('id');
			var nomeCliente = button.data('cliente');
			var logradouro = button.data('logradouro');

			var modal = $(this);
			var form = modal.find('form');
			var action = form.data('url-base');
			if (!action.endsWith('/')) {
				action += '/';
			}
			form.attr('action', action + idEndereco);

			/* Colocando a descricao do titulo no modal */
			modal.find('.modal-body span').html(
					'Tem certeza que deseja excluir o endereço <strong>'
							+ logradouro + '</strong> do cliente <strong>'
							+ nomeCliente + '</strong> ?');
		});
