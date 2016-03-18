package br.com.vsm.cadastro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vsm.cadastro.filter.ClienteFilter;
import br.com.vsm.cadastro.model.Cliente;
import br.com.vsm.cadastro.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> findAll() {
		return this.clienteRepository.findAll();
	}

	public Cliente salvar(Cliente cliente) {
		return this.clienteRepository.save(cliente);
	}
	
	public void excluir(Cliente cliente) {
		this.clienteRepository.delete(cliente);
	}

	public Cliente find(Long id) {
		return this.clienteRepository.findOne(id);
	}
	
	public List<Cliente> filtrar(ClienteFilter filter){
		String name = filter.getFiltro() == null ? "%" : filter.getFiltro();
		return this.clienteRepository.findByName(name);
	}
}
