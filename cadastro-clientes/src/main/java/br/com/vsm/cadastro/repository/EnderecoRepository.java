package br.com.vsm.cadastro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.vsm.cadastro.model.Cliente;
import br.com.vsm.cadastro.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	public List<Endereco> findByCliente(Cliente cliente);

	@Query("SELECT e FROM Endereco e where e.logradouro LIKE %:l%")
	public List<Endereco> findByLogradouro(@Param("l") String logradouro);
	
	@Modifying
	@Query("delete from Endereco where cliente = :parCliente")
	@Transactional
	public void removerEnderecosCliente(@Param("parCliente") Cliente cliente);
}
