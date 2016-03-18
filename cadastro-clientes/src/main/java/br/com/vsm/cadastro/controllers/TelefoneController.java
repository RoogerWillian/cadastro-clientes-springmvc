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

import br.com.vsm.cadastro.filter.TelefoneFilter;
import br.com.vsm.cadastro.model.Cliente;
import br.com.vsm.cadastro.model.Telefone;
import br.com.vsm.cadastro.model.TipoTelefone;
import br.com.vsm.cadastro.service.ClienteService;
import br.com.vsm.cadastro.service.TelefoneService;

@Controller
@RequestMapping("/telefones")
public class TelefoneController {

	private static final String CADASTRO_TELEFONE_VIEW = "/views/CadastroTelefone";
	private static final String LISTAGEM_TELEFONES_VIEW = "/views/ListagemTelefone";
	private static final String EDICAO_TELEFONES_VIEW = "/views/EdicaoTelefone";
	private static final String EDICAO_TELEFONES_CONSULTA_VIEW = "/views/EdicaoTelefoneListagem";
	
	private Long codigoCliente;
	private String nomeCliente;
	
	@Autowired
	private TelefoneService telefoneService;

	@Autowired
	private ClienteService clienteService;

	@RequestMapping("novo/{id}")
	public ModelAndView novo(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView(CADASTRO_TELEFONE_VIEW);
		this.codigoCliente = id;
		Telefone telefone = new Telefone();
		telefone.setCliente(this.clienteService.find(codigoCliente));
		this.nomeCliente = telefone.getCliente().getNome();
		mv.addObject(telefone);
		return mv;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String redirectCliente(@Validated Telefone telefone, Errors erros,RedirectAttributes attr) {
		if (erros.hasErrors()) {
			return CADASTRO_TELEFONE_VIEW;
		} else {
			Cliente cliente = this.clienteService.find(codigoCliente);
			telefone.setCliente(cliente);
			this.telefoneService.salvar(telefone);
			attr.addFlashAttribute(cliente);
			attr.addFlashAttribute("mensagem", "Telefone " + telefone.getNumero() + " salvo com sucesso!");
			return "redirect:/clientes/" + cliente.getId();
		}

	}
	
	@RequestMapping(value="/consulta/save/{id}",method = RequestMethod.POST)
	public String salvarListagem(@PathVariable("id")Long id, @Validated Telefone telefone, Errors erros, RedirectAttributes attrs) {
		telefone.setCliente(this.clienteService.find(id));
		if (erros.hasErrors()) {
			attrs.addFlashAttribute(telefone);
			attrs.addFlashAttribute("mensagemW", "Alterações não encontradas!");
			return "redirect:/telefones/consulta/" + telefone.getId();
		}
		try {
			this.telefoneService.salvar(telefone);
			attrs.addFlashAttribute("mensagem", "Telefone " + telefone.getNumero() + " salvo com sucesso!");
			return "redirect:/telefones";
		} catch (IllegalArgumentException e) {
			return CADASTRO_TELEFONE_VIEW;
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Telefone telefone, Errors erros, RedirectAttributes attrs) {
		if (erros.hasErrors()) {
			return CADASTRO_TELEFONE_VIEW;
		}
		try {
			this.telefoneService.salvar(telefone);
			attrs.addFlashAttribute("mensagem", "Telefone " + telefone.getNumero() + " salvo com sucesso!");
			return "redirect:/telefones/novo";
		} catch (IllegalArgumentException e) {
			return CADASTRO_TELEFONE_VIEW;
		}
	}
	
	@RequestMapping(value="/save/{id}", method = RequestMethod.POST)
	public String salvarEdicao(@PathVariable("id") Long id, @Validated Telefone telefone, Errors erros, RedirectAttributes attrs) {
		if (erros.hasErrors()) {
			attrs.addFlashAttribute(telefone);
			attrs.addFlashAttribute("mensagemW", "Alterações não encontradas!");
			return "redirect:/telefones/" + telefone.getId();
		}
		try {
			telefone.setCliente(this.clienteService.find(id));
			this.telefoneService.salvar(telefone);
			attrs.addFlashAttribute("mensagem", "Telefone " + telefone.getNumero() + " editado com sucesso!");
			return "redirect:/clientes/" + id;
		} catch (IllegalArgumentException e) {
			return EDICAO_TELEFONES_VIEW;
		}
	}
	
	@RequestMapping("/consulta/{id}")
	public ModelAndView edicaoListagem(@PathVariable("id") Telefone telefone) {
		ModelAndView mv = new ModelAndView(EDICAO_TELEFONES_CONSULTA_VIEW);
		mv.addObject(telefone);
		return mv;
	}
	
	@RequestMapping("{id}")
	public ModelAndView edicao(@PathVariable("id") Telefone telefone) {
		ModelAndView mv = new ModelAndView(EDICAO_TELEFONES_VIEW);
		mv.addObject(telefone);
		return mv;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable("id") Telefone telefone, RedirectAttributes attr) {
		Long id = telefone.getCliente().getId();
		this.telefoneService.excluir(telefone);
		attr.addFlashAttribute("mensagem", "Telefone " + telefone.getNumero() + " excluído com sucesso!");
		return "redirect:/clientes/" + id;
	}
	
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro")TelefoneFilter filter){
		ModelAndView mv = new ModelAndView(LISTAGEM_TELEFONES_VIEW);
		List<Telefone> telefones = this.telefoneService.findByNumero(filter);
		mv.addObject("telefones", telefones);
		return mv;
	}
		
	@ModelAttribute("nomeCliente")
	public String nomeCliente() {
		return this.nomeCliente;
	}
	
	@ModelAttribute("tiposTelefone")
	public List<TipoTelefone> todosTiposTelefone() {
		return Arrays.asList(TipoTelefone.values());
	}

	@ModelAttribute("clientes")
	public List<Cliente> clientes() {
		return this.clienteService.findAll();
	}

	public Long getCodigoCliente() {
		return codigoCliente;
	};
}
