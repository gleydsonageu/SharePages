package br.com.projetoapp.sharepages.negocio;

import android.util.Log;

import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.persistencia.UsuarioDAO;


public class UsuarioServices {

    private static UsuarioServices instancia = new UsuarioServices();
    public static UsuarioServices getInstancia() {
        return instancia;
    }

    private UsuarioDAO dao = UsuarioDAO.getInstancia();

    public Usuario validarCadastroUsuario(Usuario usuario) throws Exception {
        Usuario usuarioEncontrado = null;
        try {
             usuarioEncontrado = UsuarioDAO.getInstancia().consultar(usuario.getEmail(), usuario.getSenha());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Houve um erro, tente novamente..");
        }

        if (usuarioEncontrado == null) {
            throw new Exception("Usuario ou senha invalidos..");
        } else {
            return usuarioEncontrado;
        }
    }
}