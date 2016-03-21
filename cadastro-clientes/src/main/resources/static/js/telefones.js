$('#confirmacaoExclusaoTelefone').on('show.bs.modal',function(event) {
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

/* Visualizar telefones */
$('#modalVisualizarTelefones').on('show.bs.modal',function(event){
	var button = $(event.relatedTarget);
	
	var modal = $(this);
	var idCliente = button.data('id-cliente');
	var nomeCliente = button.data('cliente');
	var tableTelefones = $('#tableTelefones');
	modal.find('.modal-title').html(
			'Telefones ' + '| ' + '<strong>'+nomeCliente+'</strong>' 
	);
	
	$.get('/rest/telefones/' + idCliente, function(data){
		if(!isArrayEmpty(data)){
			$.each(data,function(){
				var telefone = this;
				
				var newRow = $('<tr>').addClass('tr-data');
				var tdId = '<td>' + telefone.id +  '</td>'
				var tdNumero = '<td>' + telefone.numero +  '</td>'
				var tdTipo = '<td>' + telefone.tipo +  '</td>'
				var tdCliente = '<td>' + telefone.cliente.nome +  '</td>'
				newRow.append(tdId);
				newRow.append(tdNumero);
				newRow.append(tdTipo);
				newRow.append(tdCliente);
				tableTelefones.append(newRow);
			});
		}else{
			var newRow = $('<tr>').addClass('tr-data');
			var td = '<td colspan=4>Este(a) cliente não possui telefones cadastrados!</td>'
			newRow.append(td);
			tableTelefones.append(newRow);
		}
	});
});


$('#modalVisualizarTelefones').on('hide.bs.modal',function(event){
	console.log('Fechou modal');
	$('.tr-data').remove();
});

function isArrayEmpty(array) {
    return array.filter(function(el) {
        return !jQuery.isEmptyObject(el);
    }).length === 0;
}