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
				$.growl.error({ title: 'CEP', message: 'Buscando informações do cep: ' + cep });
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

$('#confirmacaoExclusaoEndereco').on('show.bs.modal',function(event) {
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

$('#modalVisualizarEnderecos').on('show.bs.modal',function(event){
	var button = $(event.relatedTarget);
	
	var modal = $(this);
	var idCliente = button.data('id-cliente');
	var nomeCliente = button.data('cliente');
	var tableEnderecos = $('#tableEnderecos');
	
	modal.find('.modal-title').html(
			'Endereços ' + '| ' + '<strong>'+nomeCliente+'</strong>' 
	);
	
	$.get('/rest/enderecos/' + idCliente,function(data){
		if(!isArrayEmpty(data)){
			$.each(data,function(){
				var endereco = this;
							
				var newRow = $('<tr>').addClass('tr-data');
				var tdCep ='<td>'+endereco.cep+'</td>'
				var tdLogradouro ='<td>'+endereco.logradouro+'</td>'
				var tdBairro ='<td>'+endereco.bairro+'</td>'
				var tdNumero ='<td>'+endereco.numero+'</td>'
				var tdCidade ='<td>'+endereco.cidade+'</td>'
				var tdEstado ='<td>'+endereco.estado+'</td>'
				var tdTipo ='<td>'+endereco.tipo+'</td>'
				
				newRow.append(tdCep);
				newRow.append(tdLogradouro);
				newRow.append(tdBairro);
				newRow.append(tdNumero);
				newRow.append(tdCidade);
				newRow.append(tdEstado);
				newRow.append(tdTipo);
				tableEnderecos.append(newRow);
			});
		}else{
			var newRow = $('<tr>').addClass('tr-data');
			var td = '<td colspan=7>Este(a) cliente não possui enderecos cadastrados!</td>'
			newRow.append(td);
			tableEnderecos.append(newRow);
		}
	});
});

$('#modalVisualizarEnderecos').on('hide.bs.modal',function(){
	$('.tr-data').remove();
});

function isArrayEmpty(array) {
    return array.filter(function(el) {
        return !jQuery.isEmptyObject(el);
    }).length === 0;
}
