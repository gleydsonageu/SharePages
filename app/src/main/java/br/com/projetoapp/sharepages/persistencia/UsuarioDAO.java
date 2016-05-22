package br.com.projetoapp.sharepages.persistencia;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.gui.TelaInicial;


public class UsuarioDAO {

    public DatabaseHelper databaseHelper = new DatabaseHelper(TelaInicial.getContext());

    private static UsuarioDAO instancia = new UsuarioDAO();
    public static UsuarioDAO getInstancia() {
        return instancia;
    }


    public Usuario consultar(String email, String senha) {
        Usuario usuarioEncontrado = null;
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String query = DatabaseHelper.USUARIO_EMAIL + " = '" + email + "' AND "
            + DatabaseHelper.USUARIO_SENHA + " = '" + senha + "'";

        Cursor cursor = database.query(DatabaseHelper.TABLE_USUARIOS, DatabaseHelper.USUARIO_COLUNAS, query, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int idUsuario = cursor.getInt(0);
            String nomeUsuario = cursor.getString(1);
            String emailUsuario = cursor.getString(2);
            String senhaUsuario = cursor.getString(3);
            usuarioEncontrado = new Usuario();
            usuarioEncontrado.setId(idUsuario);
            usuarioEncontrado.setNome(nomeUsuario);
            usuarioEncontrado.setEmail(emailUsuario);
            usuarioEncontrado.setSenha(senhaUsuario);

            cursor.moveToNext();
        }
        cursor.close();

        return usuarioEncontrado;
    }

}