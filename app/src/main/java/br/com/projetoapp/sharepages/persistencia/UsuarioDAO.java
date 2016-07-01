package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;

/**
 * Classe de acesso ao Tabela Usuario no DatabaseHelper
 */
public class UsuarioDAO {

    public static UsuarioDAO getInstancia() {
        UsuarioDAO instancia = new UsuarioDAO();
        return instancia;
    }

    /**
     * Metodo para escrever o cadastro de usuário no banco com as informções: nome, email, senha e id da cidade.
     * @param usuario
     * @return
     * @throws SharepagesException
     */
    public long inserirUsuario(Usuario usuario) throws SharepagesException{

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());

        try (SQLiteDatabase database = databaseHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.USUARIO_NOME, usuario.getNome());
            values.put(DatabaseHelper.USUARIO_EMAIL, usuario.getEmail());
            values.put(DatabaseHelper.USUARIO_SENHA, usuario.getSenha());
            values.put(DatabaseHelper.USUARIO_ID_CIDADE, usuario.getIdCidade());

            long retorno = database.insert(DatabaseHelper.TABLE_USUARIOS, null, values);
            database.close();
            return retorno;
        }
    }

    /**
     * Consulta o usuario no banco verificando o email e senha.
     * @param email e-mail do usuario que esta sendo consultado
     * @param senha senha do usuario que esta sendo consultado
     * @return
     */
    public Usuario consultarCredenciaisDeUsuario(String email, String senha) {
        Usuario usuarioEncontrado = null;

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String filtro = DatabaseHelper.USUARIO_EMAIL + " = ? AND "
                + DatabaseHelper.USUARIO_SENHA + " = ?";

        Cursor cursor = database.query(DatabaseHelper.TABLE_USUARIOS, DatabaseHelper.USUARIO_COLUNAS, filtro,
                new String[]{ email, senha }, null, null, null);

        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            usuarioEncontrado = objetoUsuario(cursor);
            cursor.moveToNext();
        }

        database.close();
        return usuarioEncontrado;
    }

    /**
     * Usando uma query consulta a tabela usuaria, buscando por email.
     * @param email
     * @return email do usuario
     */
    public Usuario buscarUsuarioPorEmail(String email){
        Usuario UsuarioemailEncontrado = null;

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String filtro = DatabaseHelper.USUARIO_EMAIL + " = ?";
        Cursor cursor = database.query(DatabaseHelper.TABLE_USUARIOS, DatabaseHelper.USUARIO_COLUNAS, filtro,
                new String[]{email}, null, null, null);

        if(cursor.moveToFirst()){
            UsuarioemailEncontrado = objetoUsuario(cursor);
            cursor.moveToNext();
        }
        database.close();
        return UsuarioemailEncontrado;
    }

    /**
     * Altera o perfil de usuario por nome, senha e cidade.
     * @param usuario
     * @return
     * @throws SharepagesException
     */
    public long alterarUsuario(Usuario usuario) throws SharepagesException {

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.USUARIO_NOME, usuario.getNome());
        if (usuario.getSenha() != null && !usuario.getSenha().equals("")) {
            values.put(DatabaseHelper.USUARIO_SENHA, usuario.getSenha());
        }
        values.put(DatabaseHelper.USUARIO_ID_CIDADE, usuario.getIdCidade());

        String id = String.valueOf(usuario.getId());
        String[] whereArgs = new String[]{id};
        long retorno = database.update(DatabaseHelper.TABLE_USUARIOS, values, "id=?", whereArgs);
        database.close();
        return retorno;
    }

    /**
     * Usando uma query consulta a tabela usuária, buscando por ID do usuário.
     * @param id
     * @return usuario encontrado
     */
    public Usuario buscarPorId(int id) {
        Usuario usuarioEncontrado = null;

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String filtro = DatabaseHelper.USUARIO_ID + " =? ";
        Cursor cursor = database.query(DatabaseHelper.TABLE_USUARIOS, DatabaseHelper.USUARIO_COLUNAS, filtro,
                new String[]{String.valueOf(id)}, null, null, null);

        cursor.moveToFirst();

        usuarioEncontrado = objetoUsuario(cursor);

        database.close();
        return usuarioEncontrado;
    }

    /**
     * @param cursor cursor para carregar o objeto usuario
     * @return usuario
     */
    public Usuario objetoUsuario(Cursor cursor){
        Usuario usuario = null;

            usuario = new Usuario();
            usuario.setId(cursor.getInt(0));
            usuario.setNome(cursor.getString(1));
            usuario.setEmail(cursor.getString(2));
            usuario.setSenha(cursor.getString(3));
            usuario.setIdCidade(cursor.getInt(4));

        return usuario;
    }
}