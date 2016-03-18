package br.com.vsm.cadastro.model;

public enum StatusCadastro {

	PRE_CADASTRO("PRÉ CADASTRO");
	private String descricao;

	StatusCadastro(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
