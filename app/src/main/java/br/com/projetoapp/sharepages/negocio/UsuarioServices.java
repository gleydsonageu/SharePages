package br.com.projetoapp.sharepages.negocio;

import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.persistencia.UsuarioDAO;


public class UsuarioServices {

    private static UsuarioServices instancia = new UsuarioServices();

    public static UsuarioServices getInstancia() {
        return instancia;
    }

    private UsuarioDAO dao = UsuarioDAO.getInstancia();

    public Usuario validarCadastroUsuario(Usuario usuario) throws Exception {
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
        try {
            dao.salvar(usuario);

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("erro ao inserir no banco de dados");
        }
    }

//    public boolean validEmail(String email) {
//        //System.out.println("Metodo de validacao de email");
//        Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+\n" +
//                "(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$");
//        Matcher m = p.matcher(email);
//        if (m.find()){
//            System.out.println("O email "+email+" e valido");
//            Toast.makeText(getApplication(), "O email " + email + " e valido", Toast.LENGTH_LONG).show();
//            return true;
//        }
//        else{
//            Toast.makeText(getApplication(), "O E-mail " + email + " é inválido", Toast.LENGTH_LONG).show();
//            return false;
//        }
//    }


}
