package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;


public class UsuarioDAO {

    public DatabaseHelper databaseHelper;
    private static UsuarioDAO instancia;

    //SQLiteDatabase database = databaseHelper.getReadableDatabase();

    public static UsuarioDAO getInstancia(Context context) {
        if(instancia == null){
            instancia = new UsuarioDAO();
            instancia.databaseHelper = new DatabaseHelper(context);
        }
        return instancia;
    }

    public long inserir(Usuario usuario) throws SharepagesException{
        long id = usuario.getId();

        try (SQLiteDatabase database = databaseHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.USUARIO_NOME, usuario.getNome());
            values.put(DatabaseHelper.USUARIO_EMAIL, usuario.getEmail());
            values.put(DatabaseHelper.USUARIO_SENHA, usuario.getSenha());
            values.put(DatabaseHelper.USUARIO_ID_CIDADE, usuario.getIdCidade());

            Log.i("SCRIPT", "cadastradoooo " + usuario.getEmail());

            if (id != 0) {
                String _id = String.valueOf(usuario.getId());
                String[] whereArgs = new String[]{_id};
                return database.update(DatabaseHelper.TABLE_USUARIOS, values, "_id=?", whereArgs);
            } else {
                id = database.insert(DatabaseHelper.TABLE_USUARIOS, null, values);
                return id;
            }
        }

    }

    public Usuario consultar(String email, String senha) {
        Usuario usuarioEncontrado = null;

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String query = DatabaseHelper.USUARIO_EMAIL + " = '" + email + "' AND "
                + DatabaseHelper.USUARIO_SENHA + " = '" + senha + "'";

        Cursor cursor = database.query(DatabaseHelper.TABLE_USUARIOS, DatabaseHelper.USUARIO_COLUNAS, query, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            int idUsuario = cursor.getInt(0);
            String nomeUsuario = cursor.getString(1);
            String emailUsuario = cursor.getString(2);
            String senhaUsuario = cursor.getString(3);
            int idCidade = cursor.getInt(4);
            usuarioEncontrado = new Usuario();
            usuarioEncontrado.setId(idUsuario);
            usuarioEncontrado.setNome(nomeUsuario);
            usuarioEncontrado.setEmail(emailUsuario);
            usuarioEncontrado.setSenha(senhaUsuario);
            usuarioEncontrado.setIdCidade(idCidade);

            cursor.moveToNext();
        }
        database.close();
        return usuarioEncontrado;
    }

    public Usuario buscarEmail (String email){
        Usuario emailEncontrado = null;

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_USUARIOS + " WHERE "
                + DatabaseHelper.USUARIO_EMAIL + " = ? " ,new String[]{email});

        if (cursor.moveToFirst()){
            int idUsuario = cursor.getInt(0);
            String nomeUsuario = cursor.getString(1);
            String emailUsuario = cursor.getString(2);
            String senhaUsuario = cursor.getString(3);
            int idCidade = cursor.getInt(4);
            emailEncontrado = new Usuario();
            emailEncontrado.setId(idUsuario);
            emailEncontrado.setNome(nomeUsuario);
            emailEncontrado.setNome(emailUsuario);
            emailEncontrado.setNome(senhaUsuario);
            emailEncontrado.setIdCidade(idCidade);

            cursor.moveToNext();
        }
        database.close();
        return emailEncontrado;
    }

    public long alterar(Usuario usuario) throws SharepagesException {
        long id = usuario.getId();

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.USUARIO_NOME, usuario.getNome());
            values.put(DatabaseHelper.USUARIO_EMAIL, usuario.getEmail());
            values.put(DatabaseHelper.USUARIO_SENHA, usuario.getSenha());
            values.put(DatabaseHelper.USUARIO_ID_CIDADE, usuario.getIdCidade());

        if (id != 0) {
                String _id = String.valueOf(usuario.getId());
                String[] whereArgs = new String[]{_id};
                return database.update(DatabaseHelper.TABLE_USUARIOS, values, "_id=?", whereArgs);
        }else {
            return id;
        }
    }
}
