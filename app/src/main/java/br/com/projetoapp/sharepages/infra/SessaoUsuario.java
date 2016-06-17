package br.com.projetoapp.sharepages.infra;

import android.content.Context;

import br.com.projetoapp.sharepages.dominio.Usuario;


public class SessaoUsuario {

    private static SessaoUsuario instancia = new SessaoUsuario();
    private Usuario usuarioLogado;
    private Context context;

    public SessaoUsuario() {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
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