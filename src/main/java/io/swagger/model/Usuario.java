package io.swagger.model;

import java.util.Date;

public class Usuario {

	private Long id;	
	private String login;
	private Boolean inativo;
	private Date datainativo;	
	private String uidusuariofirebase;
	private String nome;	
	private String email;
	private String cpf;
	private boolean bloqueado;
	private Boolean usuarioCrm;
	private Boolean excluido;
	private Integer codigosupera;
	private String cep;
	private String endereco;
	private String bairro;
	private String complemento;
	private String numero;
	private String latitude;
	private String longitude;
	private String urlImagem;
	private String base;
	private String ip;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Boolean getInativo() {
		return inativo;
	}
	public void setInativo(Boolean inativo) {
		this.inativo = inativo;
	}
	public Date getDatainativo() {
		return datainativo;
	}
	public void setDatainativo(Date datainativo) {
		this.datainativo = datainativo;
	}
	public String getUidusuariofirebase() {
		return uidusuariofirebase;
	}
	public void setUidusuariofirebase(String uidusuariofirebase) {
		this.uidusuariofirebase = uidusuariofirebase;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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

	public boolean isBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	public Boolean getUsuarioCrm() {
		return usuarioCrm;
	}
	public void setUsuarioCrm(Boolean usuarioCrm) {
		this.usuarioCrm = usuarioCrm;
	}
	public Boolean getExcluido() {
		return excluido;
	}
	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}
	public Integer getCodigosupera() {
		return codigosupera;
	}
	public void setCodigosupera(Integer codigosupera) {
		this.codigosupera = codigosupera;
	}

	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getUrlImagem() {
		return urlImagem;
	}
	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	} 
	
	
	
}
