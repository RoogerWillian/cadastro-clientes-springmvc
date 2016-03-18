package br.com.vsm.cadastro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.vsm.cadastro.model.Cliente;
import br.com.vsm.cadastro.model.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, Long>{
	public List<Telefone> findByCliente(Cliente cliente);
	
	public List<Telefone> findByNumeroContaining(String numero);
	
	@Modifying
	@Query("delete from Telefone where cliente = :parCliente")
	@Transactional
	public void removerTelefonesCliente(@Param("parCliente") Cliente cliente);
}
