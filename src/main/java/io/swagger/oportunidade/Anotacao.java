package io.swagger.oportunidade;

import java.time.LocalDateTime;

public class Anotacao {
	
	private Long id;
	private LocalDateTime dataCadastro;
	private String descricao;	
	private Integer idOportunidade;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getIdOportunidade() {
		return idOportunidade;
	}
	public void setIdOportunidade(Integer idOportunidade) {
		this.idOportunidade = idOportunidade;
	}

}
