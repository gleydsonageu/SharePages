package br.com.projetoapp.sharepages.negocio;

import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.UsuarioDAO;


public class UsuarioServices {

    private static UsuarioServices  instancia = new UsuarioServices();
    private UsuarioDAO usuarioDAO;

    private UsuarioServices() {
        this.usuarioDAO = UsuarioDAO.getInstancia();
    }

    public static UsuarioServices getInstancia(){
            return instancia;
    }

    public Usuario validarLoginUsuario(Usuario usuario) throws SharepagesException {
        Usuario usuarioEncontrado;
        try {
            usuarioEncontrado = usuarioDAO.consultar(usuario.getEmail(), usuario.getSenha());
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
            emailEncontrado = usuarioDAO.buscarEmail(usuario.getEmail());

        } catch (Exception e){
            e.printStackTrace();
            throw new SharepagesException("Erro ao verificar email digitado");
        }
        if (emailEncontrado != null){
            throw new SharepagesException("Email já cadastrado");
        }else {
            usuarioDAO.inserir(usuario);
        }
    }

    public void alterarUsuario(Usuario alteracaoUsuario) throws  SharepagesException{
        try {
            usuarioDAO.alterar(alteracaoUsuario);
        }catch (Exception e){
            throw new SharepagesException("Houve um erro ao alterar usuario");
        }
    }

    public void alterarPerfilUsuarioLogado(Usuario alteracaoUsuario) throws SharepagesException{
        alterarUsuario(alteracaoUsuario);
        try {
            usuarioDAO.buscarPorId(alteracaoUsuario.getId());
            Usuario usuarioSalvo = usuarioDAO.buscarPorId(alteracaoUsuario.getId());
            SessaoUsuario.getInstancia().setUsuarioLogado(usuarioSalvo);
        }catch (Exception e){
            e.printStackTrace();
            throw new SharepagesException("Houve um erro ao alterar usuario");
        }
    }

    public boolean validarSenhaAtual(String senha) {
        return SessaoUsuario.getInstancia().getUsuarioLogado().getSenha().equals(senha);
    }

}