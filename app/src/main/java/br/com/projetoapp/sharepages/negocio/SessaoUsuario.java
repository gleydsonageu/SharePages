package br.com.projetoapp.sharepages.negocio;

import br.com.projetoapp.sharepages.dominio.Usuario;


public class SessaoUsuario {
    private static SessaoUsuario instancia = new SessaoUsuario();
    private Usuario usuarioLogado;

    public SessaoUsuario() {
    }

    public static SessaoUsuario getInstancia() {
        return instancia;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }
}