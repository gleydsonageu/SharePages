package br.com.projetoapp.sharepages.negocio;

import android.content.Context;

import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.UsuarioDAO;


public class UsuarioServices {

    private UsuarioDAO dao;

    public static UsuarioServices getInstancia(Context context) {
        UsuarioServices  instancia = new UsuarioServices();
        instancia.dao = UsuarioDAO.getInstancia(context);

        return instancia;
    }

    public Usuario validarLoginUsuario(Usuario usuario) throws SharepagesException {
        Usuario usuarioEncontrado;
        try {
            usuarioEncontrado = dao.consultar(usuario.getEmail(), usuario.getSenha());
            SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
            sessaoUsuario.setUsuarioLogado(usuarioEncontrado);

        } catch (Exception e) {
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
            throw new SharepagesException("Erro ao verificar email digitado");
        }
        if (emailEncontrado != null){
            throw new SharepagesException("Email já cadastrado");
        }else {
            dao.inserir(usuario);
        }
    }

    public void alterarUsuario(Usuario alteracaoUsuario) throws  SharepagesException{
        try {
            dao.alterar(alteracaoUsuario);
        }catch (Exception e){
            throw new SharepagesException("Houve um erro ao alterar usuario");
        }
    }

    public void alterarPerfilUsuarioLogado(Usuario alteracaoUsuario) throws SharepagesException{
        alterarUsuario(alteracaoUsuario);
        try {
            dao.buscarPorId(alteracaoUsuario.getId());
            Usuario usuarioSalvo = dao.buscarPorId(alteracaoUsuario.getId());
            SessaoUsuario.getInstancia().setUsuarioLogado(usuarioSalvo);
        }catch (Exception e){
            throw new SharepagesException("Houve um erro ao alterar usuario");
        }
    }

    public boolean validarSenhaAtual(String senha) {
        return SessaoUsuario.getInstancia().getUsuarioLogado().getSenha().equals(senha);
    }



}