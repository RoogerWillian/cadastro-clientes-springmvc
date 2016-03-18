package br.com.vsm.cadastro.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vsm.cadastro.model.Endereco;
import br.com.vsm.cadastro.model.Telefone;
import br.com.vsm.cadastro.service.EnderecoService;
import br.com.vsm.cadastro.service.TelefoneService;

@Controller
@RequestMapping("aux")
public class AuxiliarControler {

	@Autowired
	private TelefoneService telefoneService;

	@Autowired
	private EnderecoService enderecoService;

	@RequestMapping(value = "/telefones/{idCliente}")
	public List<Telefone> getTelefonesCliente(@PathVariable("idCliente") Long idCliente) {
		return this.telefoneService.findByCliente(idCliente);
	}

	@RequestMapping(value = "/enderecos/{idCliente}")
	public @ResponseBody List<Endereco> getEnderecosCliente(@PathVariable("idCliente") Long idCliente) {
		return this.enderecoService.findByCliente(idCliente);
	}
}
