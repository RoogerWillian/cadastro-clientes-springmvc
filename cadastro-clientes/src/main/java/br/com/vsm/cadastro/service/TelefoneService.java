package br.com.vsm.cadastro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vsm.cadastro.filter.TelefoneFilter;
import br.com.vsm.cadastro.model.Cliente;
import br.com.vsm.cadastro.model.Telefone;
import br.com.vsm.cadastro.repository.TelefoneRepository;

@Service
public class TelefoneService {

	@Autowired
	private TelefoneRepository telefoneRepository;

	public void salvar(Telefone telefone) {
		this.telefoneRepository.save(telefone);
	}

	public List<Telefone> findAll() {
		return telefoneRepository.findAll();
	}

	public List<Telefone> findByCliente(Long codigo) {
		Cliente cliente = new Cliente();
		cliente.setId(codigo);
		return telefoneRepository.findByCliente(cliente);
	}

	public void excluir(Telefone tel) {
		this.telefoneRepository.delete(tel);
	}

	public Telefone find(long id) {
		return this.telefoneRepository.findOne(id);
	}

	public List<Telefone> findByNumero(TelefoneFilter filter) {
		String numero = filter.getFiltro() == null ? "%" : filter.getFiltro();
		return this.telefoneRepository.findByNumeroContaining(numero);
	}

	public void excluirTelefonesCliente(Cliente cliente) {
		this.telefoneRepository.removerTelefonesCliente(cliente);
	}

}
