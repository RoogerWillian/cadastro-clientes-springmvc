package br.com.vsm.cadastro.model;

public enum TipoLogradouro {
	RUA("Rua"), AVENIDA("Avenida"), RODOVIA("Rodovia"), TRAVESSA("Travessa"), ESTRADA("Estrada");

	private String descricao;

	private TipoLogradouro(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
