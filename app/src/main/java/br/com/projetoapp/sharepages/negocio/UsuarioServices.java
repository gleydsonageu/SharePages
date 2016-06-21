package br.com.projetoapp.sharepages.negocio;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.Criptografia;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.UsuarioDAO;

public class UsuarioServices {

    private static UsuarioServices  instancia = new UsuarioServices();
    private UsuarioDAO usuarioDAO;
    private Criptografia criptografia = new Criptografia();

    private UsuarioServices() {
        this.usuarioDAO = UsuarioDAO.getInstancia();
    }

    public static UsuarioServices getInstancia(){
            return instancia;
    }

    public Usuario validarLoginUsuario(Usuario usuario) throws SharepagesException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Usuario usuarioEncontrado;

        String senhaCriptografada = criptografia.setSenha(usuario.getSenha());

        try {
            usuarioEncontrado = usuarioDAO.consultar(usuario.getEmail(), senhaCriptografada);
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

    public void inserirUsuario(Usuario usuario) throws SharepagesException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Usuario emailEncontrado;

        String senhaCriptografada = criptografia.setSenha(usuario.getSenha());

        try {
            emailEncontrado = usuarioDAO.getEmail(usuario.getEmail());

        } catch (Exception e){
            e.printStackTrace();
            throw new SharepagesException("Erro ao verificar email digitado");
        }
        if (emailEncontrado != null){
            throw new SharepagesException("Email já cadastrado");
        }else {

            usuario.setSenha(senhaCriptografada);
            usuarioDAO.inserir(usuario);
        }
    }

    public void alterarUsuario(Usuario alteracaoUsuario) throws SharepagesException, UnsupportedEncodingException, NoSuchAlgorithmException {

        String senhaCriptografada = criptografia.setSenha(alteracaoUsuario.getSenha());

        try {
            alteracaoUsuario.setSenha(senhaCriptografada);
            usuarioDAO.alterar(alteracaoUsuario);
        }catch (Exception e){
            throw new SharepagesException("Houve um erro ao alterar usuario");
        }
    }

    public void alterarPerfilUsuarioLogado(Usuario alteracaoUsuario) throws SharepagesException, UnsupportedEncodingException, NoSuchAlgorithmException {
        alterarUsuario(alteracaoUsuario);

        try {
            usuarioDAO.getPorId(alteracaoUsuario.getId());
            Usuario usuarioSalvo = usuarioDAO.getPorId(alteracaoUsuario.getId());
            SessaoUsuario.getInstancia().setUsuarioLogado(usuarioSalvo);
        }catch (Exception e){
            e.printStackTrace();
            throw new SharepagesException("Houve um erro ao alterar usuario");
        }
    }

    public boolean validarSenhaAtual(String senha) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String senhaCriptografada = criptografia.setSenha(senha);
        return SessaoUsuario.getInstancia().getUsuarioLogado().getSenha().equals(senhaCriptografada);
    }

}