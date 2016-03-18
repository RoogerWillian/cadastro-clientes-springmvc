package br.com.vsm.cadastro.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "clientes", uniqueConstraints = @UniqueConstraint(name = "UX_CPF", columnNames = { "cpf" }) )
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(nullable = false, length = 50)
	private String nome;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataNascimento;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataCadastro;

	@NotEmpty
	@Column(nullable = false, length = 60)
	private String email;

	@NotEmpty
	@Column(nullable = false, length = 14)
	private String cpf;

	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusCliente status;

	public boolean isAtivo() {
		return StatusCliente.ATIVO.equals(this.status);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public StatusCliente getStatus() {
		return status;
	}

	public void setStatus(StatusCliente status) {
		this.status = status;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataCadastro() {
		if (this.dataCadastro == null) {
			this.dataCadastro = new Date();
		}
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", dataCadastro="
				+ dataCadastro + ", email=" + email + ", cpf=" + cpf + ", status=" + status;
	}

	// public List<Endereco> getEnderecos() {
	// if (this.enderecos == null) {
	// this.enderecos = new ArrayList<>();
	// }
	// return enderecos;
	// }
	//
	// public void setEnderecos(List<Endereco> enderecos) {
	// this.enderecos = enderecos;
	// }
	//
	// public List<Telefone> getTelefones() {
	// if (this.telefones == null) {
	// this.telefones = new ArrayList<>();
	// }
	// return telefones;
	// }
	//
	// public void setTelefones(List<Telefone> telefones) {
	// this.telefones = telefones;
	// }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
