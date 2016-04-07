package br.com.vsm.cadastro.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AutenticacaoController {
	
	private static final String AUTENTICACAO_VIEW = "/views/Autenticacao";

	@RequestMapping
	public String autenticacao(){
		return AUTENTICACAO_VIEW;
	}
}
