package br.com.vsm.cadastro;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.vsm.cadastro.model.Telefone;
import br.com.vsm.cadastro.service.TelefoneService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CadastroClientesApplication.class)
@WebAppConfiguration
public class CadastroClientesApplicationTests {

//	@Autowired
//	private EnderecoService enderecoService;

	@Autowired
	private TelefoneService telefoneService;

	// @Autowired
	// private ClienteService clienteSerice;

	@Test
	public void contextLoads() {
		try {

			Telefone telefone = this.telefoneService.find(1L);
			this.telefoneService.excluir(telefone);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
