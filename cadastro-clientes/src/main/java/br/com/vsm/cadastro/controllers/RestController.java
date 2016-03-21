package br.com.vsm.cadastro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import br.com.vsm.cadastro.service.EnderecoService;
import br.com.vsm.cadastro.service.TelefoneService;

@Controller
@RequestMapping("rest")
public class RestController {

	@Autowired
	private TelefoneService telefoneService;

	@Autowired
	private EnderecoService enderecoService;

	@RequestMapping(value = "/telefones/{idCliente}", produces = { "application/json" })
	public @ResponseBody String getTelefonesCliente(@PathVariable("idCliente") Long idCliente) {
		Gson gson = new Gson();
		return gson.toJson(this.telefoneService.findByCliente(idCliente));
	}

	@RequestMapping(value = "/enderecos/{idCliente}", produces = { "application/json" })
	public @ResponseBody String getEnderecosCliente(@PathVariable("idCliente") Long idCliente) {
		Gson gson = new Gson();
		return gson.toJson(this.enderecoService.findByCliente(idCliente));
	}
}
