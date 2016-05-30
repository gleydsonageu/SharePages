package br.com.projetoapp.sharepages.negocio;

import android.content.Context;

import br.com.projetoapp.sharepages.dominio.Usuario;
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

    public Usuario validarUsuario(Usuario usuario) throws Exception {
        Usuario usuarioEncontrado;
        try {
             usuarioEncontrado = dao.consultar(usuario.getEmail(), usuario.getSenha());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Houve um erro, tente novamente");
        }

        if (usuarioEncontrado == null) {
            throw new Exception("Usuario ou senha invalidos");
        } else {
            return usuarioEncontrado;
        }
    }

    public void inserirUsuario(Usuario usuario) throws Exception {
        Usuario emailEncontrado;
        try {
            emailEncontrado = dao.buscarEmail(usuario.getEmail());

        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Erro ao verificar email digitado");
        }
        if (emailEncontrado != null){
            throw new Exception("Email já cadastrado");
        }else {
            dao.inserir(usuario);
        }
    }

}