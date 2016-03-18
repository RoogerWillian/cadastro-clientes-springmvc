package br.com.vsm.cadastro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vsm.cadastro.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	@Query("SELECT c FROM Cliente c where c.nome LIKE %:nome%")
	public List<Cliente> findByName(@Param("nome") String nome);
}
