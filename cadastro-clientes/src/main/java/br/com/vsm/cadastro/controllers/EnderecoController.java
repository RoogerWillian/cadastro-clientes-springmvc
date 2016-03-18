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

import br.com.vsm.cadastro.filter.EnderecoFilter;
import br.com.vsm.cadastro.model.Cliente;
import br.com.vsm.cadastro.model.Endereco;
import br.com.vsm.cadastro.model.TipoEndereco;
import br.com.vsm.cadastro.model.TipoLogradouro;
import br.com.vsm.cadastro.service.ClienteService;
import br.com.vsm.cadastro.service.EnderecoService;

@Controller
@RequestMapping("/enderecos")
public class EnderecoController {

	private static final String CADASTRO_ENDERECO_VIEW = "/views/CadastroEndereco";
	private static final String LISTAGEM_ENDERECO_VIEW = "/views/ListagemEnderecos";
	private static final String EDICAO_ENDERECO_VIEW = "/views/EdicaoEndereco";
	private static final String CONSULTA_ENDERECO_VIEW = "/views/EdicaoEnderecoListagem";
	private Long codigoCliente;

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private ClienteService clienteService;

	@RequestMapping("novo/{id}")
	public ModelAndView novo(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView(CADASTRO_ENDERECO_VIEW);
		this.codigoCliente = id;
		Endereco endereco = new Endereco();
		endereco.setCliente(this.clienteService.find(codigoCliente));
		mv.addObject(endereco);
		this.codigoCliente = id;
		return mv;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String redirectCliente(@Validated Endereco endereco, Errors erros, RedirectAttributes attr) {
		Cliente cliente = this.clienteService.find(codigoCliente);
		endereco.setCliente(cliente);
		if (erros.hasErrors()) {
			return CADASTRO_ENDERECO_VIEW;
		} else {
			this.enderecoService.salvar(endereco);
			attr.addFlashAttribute(cliente);
			attr.addFlashAttribute("mensagem",
					"Endereço " + endereco.getLogradouro().toUpperCase() + " salvo com sucesso!");
			return "redirect:/clientes/" + codigoCliente;
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Endereco endereco, Errors erros, RedirectAttributes attrs) {
		if (erros.hasErrors()) {
			return CADASTRO_ENDERECO_VIEW;
		}
		try {
			this.enderecoService.salvar(endereco);
			attrs.addFlashAttribute("mensagem", "Endereço " + endereco.getLogradouro() + " salvo com sucesso!");
			return "redirect:/enderecos/novo";
		} catch (IllegalArgumentException e) {
			return CADASTRO_ENDERECO_VIEW;
		}
	}
	
	@RequestMapping(value = "consulta/save/{id}", method = RequestMethod.POST)
	public String salvarListagem(@PathVariable("id") Long id,@Validated Endereco endereco, Errors erros, RedirectAttributes attrs) {
		endereco.setCliente(this.clienteService.find(id));
		if (erros.hasErrors()) {
			return EDICAO_ENDERECO_VIEW;
		}
		try {
			this.enderecoService.salvar(endereco);
			attrs.addFlashAttribute("mensagem",
					"Endereço " + endereco.getLogradouro().toUpperCase() + " editado com sucesso!");
			return "redirect:/clientes/" + id;
		} catch (IllegalArgumentException e) {
			return EDICAO_ENDERECO_VIEW;
		}
	}
	
	@RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
	public String salvarEdicao(@PathVariable("id")Long id,@Validated Endereco endereco, Errors erros, RedirectAttributes attrs) {
		if (erros.hasErrors()) {
			attrs.addFlashAttribute(endereco);
			attrs.addFlashAttribute("mensagemW", "Alterações não encontradas!");
			return "redirect:/enderecos/" + endereco.getId();
		}
		try {
			endereco.setCliente(this.clienteService.find(id));
			this.enderecoService.salvar(endereco);
			attrs.addFlashAttribute("mensagem",
					"Endereço " + endereco.getLogradouro().toUpperCase() + " editado com sucesso!");
			return "redirect:/clientes/" + id;
		} catch (IllegalArgumentException e) {
			return EDICAO_ENDERECO_VIEW;
		}
	}

	@RequestMapping
	public ModelAndView pesquisa(@ModelAttribute("filtro") EnderecoFilter filter) {
		ModelAndView mv = new ModelAndView(LISTAGEM_ENDERECO_VIEW);
		List<Endereco> enderecos = this.enderecoService.filterByLogradouro(filter);
		mv.addObject("enderecos", enderecos);
		return mv;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable("id") Endereco endereco, RedirectAttributes attr) {
		Long idCliente = endereco.getCliente().getId();
		this.enderecoService.excluir(endereco.getId());
		attr.addFlashAttribute("mensagem",
				"Endereço " + endereco.getLogradouro().toUpperCase() + " excluído com sucesso!");
		return "redirect:/clientes/" + idCliente;
	}
	
	@RequestMapping("/consulta/{id}")
	public ModelAndView edicaoListagem(@PathVariable("id") Endereco endereco) {
		ModelAndView mv = new ModelAndView(CONSULTA_ENDERECO_VIEW);
		mv.addObject(endereco);
		return mv;
	}
	
	@RequestMapping("{id}")
	public ModelAndView edicao(@PathVariable("id") Endereco endereco) {
		ModelAndView mv = new ModelAndView(EDICAO_ENDERECO_VIEW);
		mv.addObject(endereco);
		return mv;
	}

	@ModelAttribute("tiposEndereco")
	public List<TipoEndereco> tiposEndereco() {
		return Arrays.asList(TipoEndereco.values());
	}

	@ModelAttribute("tiposLogradouro")
	public List<TipoLogradouro> tiposLogradouro() {
		return Arrays.asList(TipoLogradouro.values());
	}

	@ModelAttribute("clientes")
	public List<Cliente> clientes() {
		return this.clienteService.findAll();
	}

	@ModelAttribute("endereco")
	public Endereco endereco() {
		return new Endereco();
	}

	public Long getCodigoCliente() {
		return codigoCliente;
	}
}
