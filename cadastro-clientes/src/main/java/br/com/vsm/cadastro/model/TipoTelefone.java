package br.com.vsm.cadastro.model;

public enum TipoTelefone {
	PESSOAL("Pessoal"), COMERCIAL("Comercial"), TRABALHO("Trabalho");

	private String descricao;

	TipoTelefone(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
