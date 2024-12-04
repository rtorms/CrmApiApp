package io.swagger.oportunidade;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Oportunidade implements Serializable {

	private static final long serialVersionUID = -787240228060835435L;

	private Integer id;
	private LocalDateTime dataCadastro;
	private LocalDateTime dataConquistaPerda;
	private String descricao;
	private Integer situacaoOportunidade;
	private Integer idCliente;
	private String nomeCliente;
	private Double valor;
	private List<Anotacao> anotacoes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
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

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
}
