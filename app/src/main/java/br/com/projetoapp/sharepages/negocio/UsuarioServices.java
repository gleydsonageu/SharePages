package br.com.projetoapp.sharepages.negocio;

import android.content.Context;

import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.UsuarioDAO;


public class UsuarioServices {

    private static UsuarioServices instancia;
    private UsuarioDAO dao;

    public static UsuarioServices getInstancia(Context context) {
        if(instancia == null){
            instancia = new UsuarioServices();
            instancia.dao = UsuarioDAO.getInstancia(context);
        }
        return instancia;
    }

    public Usuario validarLoginUsuario(Usuario usuario) throws SharepagesException {
        Usuario usuarioEncontrado;
        try {
            usuarioEncontrado = dao.consultar(usuario.getEmail(), usuario.getSenha());
            SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
            sessaoUsuario.setUsuarioLogado(usuarioEncontrado);

        } catch (Exception e) {
            e.printStackTrace();
            throw new SharepagesException("Houve um erro, tente novamente");
        }

        if (usuarioEncontrado == null) {
            throw new SharepagesException("Usuario ou senha invalidos");
        } else {
            return usuarioEncontrado;
        }
    }

    public void inserirUsuario(Usuario usuario) throws SharepagesException {
        Usuario emailEncontrado;
        try {
            emailEncontrado = dao.buscarEmail(usuario.getEmail());

        } catch (Exception e){
            e.printStackTrace();
            throw new SharepagesException("Erro ao verificar email digitado");
        }
        if (emailEncontrado != null){
            throw new SharepagesException("Email já cadastrado");
        }else {
            dao.inserir(usuario);
        }
    }

    public void alterarUsuario(Usuario usuario) throws  SharepagesException{
        long id;
        try {
            id = dao.alterar(usuario);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}