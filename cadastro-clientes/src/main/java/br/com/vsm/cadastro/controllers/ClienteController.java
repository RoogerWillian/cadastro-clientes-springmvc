package br.com.vsm.cadastro.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vsm.cadastro.filter.ClienteFilter;
import br.com.vsm.cadastro.model.Cliente;
import br.com.vsm.cadastro.model.Endereco;
import br.com.vsm.cadastro.model.StatusCliente;
import br.com.vsm.cadastro.model.Telefone;
import br.com.vsm.cadastro.model.TipoEndereco;
import br.com.vsm.cadastro.model.TipoLogradouro;
import br.com.vsm.cadastro.service.ClienteService;
import br.com.vsm.cadastro.service.EnderecoService;
import br.com.vsm.cadastro.service.TelefoneService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	private static final String CADASTRO_CLIENTE_VIEW = "/views/CadastroClientes";
	private static final String CONSULTA_CLIENTE_VIEW = "/views/ConsultaClientes";

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private TelefoneService telefoneService;

	@Autowired
	private EnderecoService enderecoService;

	@RequestMapping("/novo")
	public String novo() {
		return CADASTRO_CLIENTE_VIEW;
	}

	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") ClienteFilter clienteFilter) {
		ModelAndView mv = new ModelAndView(CONSULTA_CLIENTE_VIEW);
		List<Cliente> clientes = clienteService.filtrar(clienteFilter);
		mv.addObject("clientes", clientes);
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Cliente cliente, Errors erros, RedirectAttributes attrs) {
		if (erros.hasErrors()) {
			return CADASTRO_CLIENTE_VIEW;
		}
		try {
			this.clienteService.salvar(cliente);
			attrs.addFlashAttribute("mensagem", "Cliente " + cliente.getNome().toUpperCase() + " salvo com sucesso!");
			return "redirect:/clientes/";
		} catch (Exception e) {
			attrs.addFlashAttribute("mensagem","CPF já cadastrado para outro cliente, tente novamente!");
			return "redirect:/clientes/novo";
		} 
	}

	@RequestMapping("{id}")
	public ModelAndView edicao(@PathVariable("id") Cliente cliente) {
		ModelAndView mv = new ModelAndView(CADASTRO_CLIENTE_VIEW);
		List<Telefone> telefones = this.telefoneService.findByCliente(cliente.getId());
		List<Endereco> enderecos = this.enderecoService.findByCliente(cliente.getId());
		mv.addObject(cliente);
		mv.addObject("telefones", telefones);
		mv.addObject("enderecos", enderecos);
		return mv;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable("id") Cliente cliente, RedirectAttributes attr) {
		this.enderecoService.excluirEnderecosCliente(cliente);
		this.telefoneService.excluirTelefonesCliente(cliente);
		this.clienteService.excluir(cliente);
		attr.addFlashAttribute("mensagem", "Cliente " + cliente.getNome().toUpperCase() + " excluído com sucesso!");
		return "redirect:/clientes";
	}

	@RequestMapping("/addTelefone")
	public String addTelefone(@Validated Cliente cliente, Errors erros, RedirectAttributes attr) {
		if (erros.hasErrors()) {
			return CADASTRO_CLIENTE_VIEW;
		} else {
			Cliente clienteSalvo = this.clienteService.salvar(cliente);
			attr.addFlashAttribute("nomeCliente", cliente.getNome());
			return "redirect:/telefones/novo/" + clienteSalvo.getId();
		}
	}

	@RequestMapping("/addEndereco")
	public String addEndereco(@Validated Cliente cliente, Errors erros, RedirectAttributes attr) {
		if (erros.hasErrors()) {
			return CADASTRO_CLIENTE_VIEW;
		} else {
			Cliente clienteSalvo = this.clienteService.salvar(cliente);
			attr.addFlashAttribute("nomeCliente", cliente.getNome());
			return "redirect:/enderecos/novo/" + clienteSalvo.getId();
		}
	}

	/* ModelAttribute's */
	@ModelAttribute("cliente")
	public Cliente cliente() {
		return new Cliente();
	}

	@ModelAttribute("todosStatusCliente")
	public List<StatusCliente> todosStatusCliente() {
		return Arrays.asList(StatusCliente.values());
	}

	@ModelAttribute("tiposLogradouro")
	public List<TipoLogradouro> todosTiposLogradouro() {
		return Arrays.asList(TipoLogradouro.values());
	}

	@ModelAttribute("tiposEndereco")
	public List<TipoEndereco> todosTiposEndereco() {
		return Arrays.asList(TipoEndereco.values());
	}

	@ModelAttribute("clientes")
	public List<Cliente> clientes(){
		return this.clienteService.findAll();
	}
}
