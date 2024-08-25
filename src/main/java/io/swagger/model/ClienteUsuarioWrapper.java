package io.swagger.model;

import io.swagger.cliente.Cliente;

public class ClienteUsuarioWrapper {

    private Cliente cliente;
    private Usuario usuario;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

