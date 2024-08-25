package io.swagger.model;

import io.swagger.oportunidade.Oportunidade;

public class OportunidadeUsuarioWrapper {

    private Oportunidade oportunidade;
    private Usuario usuario;

    public Oportunidade getOportunidade() {
        return oportunidade;
    }

    public void setOportunidade(Oportunidade oportunidade) {
        this.oportunidade = oportunidade;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

