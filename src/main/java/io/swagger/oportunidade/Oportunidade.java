package io.swagger.oportunidade;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Oportunidade implements Serializable {

	private static final long serialVersionUID = 2886309453909142592L;

	private Long id;
	private LocalDateTime dataCadastro;
	private LocalDateTime dataConquistaPerda;
	private String descricao;
	private Integer situacaoOportunidade;
	private Long idCliente;
	private Double valor;
	private List<Anotacao> anotacoes;

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

	public LocalDateTime getDataConquistaPerda() {
		return dataConquistaPerda;
	}

	public void setDataConquistaPerda(LocalDateTime dataConquistaPerda) {
		this.dataConquistaPerda = dataConquistaPerda;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getSituacaoOportunidade() {
		return situacaoOportunidade;
	}

	public void setSituacaoOportunidade(Integer situacaoOportunidade) {
		this.situacaoOportunidade = situacaoOportunidade;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public List<Anotacao> getAnotacoes() {
		return anotacoes;
	}

	public void setAnotacoes(List<Anotacao> anotacoes) {
		this.anotacoes = anotacoes;
	}
	
}
