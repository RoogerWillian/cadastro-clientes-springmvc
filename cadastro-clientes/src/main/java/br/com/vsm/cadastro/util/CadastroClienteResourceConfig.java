package br.com.vsm.cadastro.util;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("rest")
public class CadastroClienteResourceConfig extends ResourceConfig{
	public CadastroClienteResourceConfig() {
		super.packages("br.com.vsm.cadastro.service");
	}
}
