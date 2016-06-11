package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.negocio.SessaoUsuario;


public class UsuarioDAO {

    public static UsuarioDAO getInstancia() {
        UsuarioDAO instancia = new UsuarioDAO();
        return instancia;
    }

    public long inserir(Usuario usuario) throws SharepagesException{
        SessaoUsuario sessao = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessao.getContext());

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

    public Usuario consultar(String email, String senha) {
        Usuario usuarioEncontrado = null;

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

    public Usuario buscarEmail (String email){
        Usuario emailEncontrado = null;

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String filtro = DatabaseHelper.USUARIO_EMAIL + " = ?";
        Cursor cursor = database.query(DatabaseHelper.TABLE_USUARIOS, DatabaseHelper.USUARIO_COLUNAS, filtro,
                new String[]{email}, null, null, null);

        if(cursor.moveToFirst()){
            emailEncontrado = objetoUsuario(cursor);
            cursor.moveToNext();
        }
        database.close();
        return emailEncontrado;
    }

    public long alterar(Usuario usuario) throws SharepagesException {

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.USUARIO_NOME, usuario.getNome());
        if (usuario.getSenha() != null && !usuario.getSenha().equals("")) {
            values.put(DatabaseHelper.USUARIO_SENHA, usuario.getSenha());
        }
        values.put(DatabaseHelper.USUARIO_ID_CIDADE, usuario.getIdCidade());

        String _id = String.valueOf(usuario.getId());
        String[] whereArgs = new String[]{_id};
        long retorno = database.update(DatabaseHelper.TABLE_USUARIOS, values, "id=?", whereArgs);
        database.close();
        return retorno;
    }

    public Usuario buscarPorId (int id) {
        Usuario usuarioEncontrado = null;

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String filtro = DatabaseHelper.USUARIO_ID + " =? ";
        Cursor cursor = database.query(DatabaseHelper.TABLE_USUARIOS, DatabaseHelper.USUARIO_COLUNAS, filtro,
                new String[]{String.valueOf(id)}, null, null, null);

        cursor.moveToFirst();

        usuarioEncontrado = objetoUsuario(cursor);

        database.close();
        return usuarioEncontrado;
    }

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