package br.com.projetoapp.sharepages.negocio;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.Criptografia;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.UsuarioDAO;

/**
 * UsuarioServices trata solicitações na UsuarioDAO
 */
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

    /**
     * Valida login e senha de usuario no UsuarioDAO
     * @param usuario
     * @return
     * @throws SharepagesException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public Usuario validarLoginUsuario(Usuario usuario) throws SharepagesException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Usuario usuarioEncontrado;

        String senhaCriptografada = criptografia.setSenha(usuario.getSenha());

        try {
            usuarioEncontrado = usuarioDAO.consultarCredenciaisDeUsuario(usuario.getEmail(), senhaCriptografada);
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

    /**
     * Faz a requisição no banco por email e solicita ao usuarioDAO a inserção do usuário.
     * @param usuario
     * @throws SharepagesException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public void inserirUsuario(Usuario usuario) throws SharepagesException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Usuario emailEncontrado;

        String senhaCriptografada = criptografia.setSenha(usuario.getSenha());

        try {
            emailEncontrado = usuarioDAO.buscarUsuarioPorEmail(usuario.getEmail());

        } catch (Exception e){
            throw new SharepagesException("Erro ao verificar email digitado");
        }
        if (emailEncontrado != null){
            throw new SharepagesException("Email já cadastrado");
        }else {

            usuario.setSenha(senhaCriptografada);
            usuarioDAO.inserirUsuario(usuario);
        }
    }

    
    public void alterarPerfilUsuario(Usuario alteracaoUsuario) throws SharepagesException, UnsupportedEncodingException, NoSuchAlgorithmException {

        String senhaCriptografada = criptografia.setSenha(alteracaoUsuario.getSenha());

        try {
            alteracaoUsuario.setSenha(senhaCriptografada);
            usuarioDAO.alterarUsuario(alteracaoUsuario);
        }catch (Exception e){
            throw new SharepagesException("Houve um erro ao alterar usuario");
        }
    }

    public void alterarPerfilUsuarioLogado(Usuario alteracaoUsuario) throws SharepagesException, UnsupportedEncodingException, NoSuchAlgorithmException {
        alterarPerfilUsuario(alteracaoUsuario);

        try {
            usuarioDAO.buscarPorId(alteracaoUsuario.getId());
            Usuario usuarioSalvo = usuarioDAO.buscarPorId(alteracaoUsuario.getId());
            SessaoUsuario.getInstancia().setUsuarioLogado(usuarioSalvo);
        }catch (Exception e){
            throw new SharepagesException("Houve um erro ao alterar usuario");
        }
    }

    public boolean getSenhaAtual(String senha) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String senhaCriptografada = criptografia.setSenha(senha);
        return SessaoUsuario.getInstancia().getUsuarioLogado().getSenha().equals(senhaCriptografada);
    }

}