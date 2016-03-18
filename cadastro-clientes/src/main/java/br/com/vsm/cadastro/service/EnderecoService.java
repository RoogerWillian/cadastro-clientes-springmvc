package br.com.vsm.cadastro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vsm.cadastro.filter.EnderecoFilter;
import br.com.vsm.cadastro.model.Cliente;
import br.com.vsm.cadastro.model.Endereco;
import br.com.vsm.cadastro.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	public List<Endereco> findByCliente(Long codigo) {
		Cliente cliente = new Cliente();
		cliente.setId(codigo);
		return this.enderecoRepository.findByCliente(cliente);
	}

	public void salvar(Endereco endereco) {
		this.enderecoRepository.save(endereco);
	}

	public List<Endereco> findAll() {
		return this.enderecoRepository.findAll();
	}

	public void excluir(Long id) {
		this.enderecoRepository.delete(id);
	}

	public List<Endereco> filterByLogradouro(EnderecoFilter filter) {
		String filtro = filter.getFiltro() == null ? "%" : filter.getFiltro();
		return this.enderecoRepository.findByLogradouro(filtro);
	}

	public void excluirEnderecosCliente(Cliente cliente) {
		this.enderecoRepository.removerEnderecosCliente(cliente);
	}

}
