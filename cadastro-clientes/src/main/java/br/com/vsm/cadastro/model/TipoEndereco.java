package br.com.vsm.cadastro.model;

public enum TipoEndereco {
	RESIDENCIAL("Residencial"), COMERCIAL("Comercial"), TRABALHO("Trabalho");
	
	private String descricao;
	
	private TipoEndereco(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
