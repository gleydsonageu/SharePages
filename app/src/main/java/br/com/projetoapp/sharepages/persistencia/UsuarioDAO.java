package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;


public class UsuarioDAO {

    public DatabaseHelper databaseHelper;
    private static UsuarioDAO instancia;

//    public static UsuarioDAO getInstancia(Context context) {
//            instancia = new UsuarioDAO();
//            instancia.databaseHelper = new DatabaseHelper(context);
//        return instancia;
//    }

    public static UsuarioDAO getInstancia(Context context) {
        if(instancia == null){
            instancia = new UsuarioDAO();
            instancia.databaseHelper = new DatabaseHelper(context);
        }
        return instancia;
    }

    public long inserir(Usuario usuario) throws SharepagesException{

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

//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            int idUsuario = cursor.getInt(0);
//            String nomeUsuario = cursor.getString(1);
//            String emailUsuario = cursor.getString(2);
//            String senhaUsuario = cursor.getString(3);
//            int idCidade = cursor.getInt(4);
//            usuarioEncontrado = new Usuario();
//            usuarioEncontrado.setId(idUsuario);
//            usuarioEncontrado.setNome(nomeUsuario);
//            usuarioEncontrado.setEmail(emailUsuario);
//            usuarioEncontrado.setSenha(senhaUsuario);
//            usuarioEncontrado.setIdCidade(idCidade);
//
//            cursor.moveToNext();
//        }
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

//        if (cursor.moveToFirst()){
//            int idUsuario = cursor.getInt(0);
//            String nomeUsuario = cursor.getString(1);
//            String emailUsuario = cursor.getString(2);
//            String senhaUsuario = cursor.getString(3);
//            int idCidade = cursor.getInt(4);
//            emailEncontrado = new Usuario();
//            emailEncontrado.setId(idUsuario);
//            emailEncontrado.setNome(nomeUsuario);
//            emailEncontrado.setNome(emailUsuario);
//            emailEncontrado.setNome(senhaUsuario);
//            emailEncontrado.setIdCidade(idCidade);
//
//            cursor.moveToNext();
//        }
        database.close();
        return emailEncontrado;
    }

    public long alterar(Usuario usuario) throws SharepagesException {

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.USUARIO_NOME, usuario.getNome());
        values.put(DatabaseHelper.USUARIO_SENHA, usuario.getSenha());
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
        String filtro = DatabaseHelper.USUARIO_ID + "=?";
        Cursor cursor = database.query(DatabaseHelper.TABLE_USUARIOS, DatabaseHelper.USUARIO_COLUNAS, filtro,
                new String[]{String.valueOf(id)}, null, null, null);

        cursor.moveToFirst();

        usuarioEncontrado = objetoUsuario(cursor);

//        int idUsuario = cursor.getInt(0);
//        String nomeUsuario = cursor.getString(1);
//        String emailUsuario = cursor.getString(2);
//        String senhaUsuario = cursor.getString(3);
//        int idCidade = cursor.getInt(4);
//        usuarioEncontrado = new Usuario();
//        usuarioEncontrado.setId(idUsuario);
//        usuarioEncontrado.setNome(nomeUsuario);
//        usuarioEncontrado.setEmail(emailUsuario);
//        usuarioEncontrado.setSenha(senhaUsuario);
//        usuarioEncontrado.setIdCidade(idCidade);

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