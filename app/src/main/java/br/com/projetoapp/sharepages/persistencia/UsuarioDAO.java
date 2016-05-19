package br.com.projetoapp.sharepages.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.com.projetoapp.sharepages.dominio.Usuario;

/**
 * Created by gleydson on 17/05/16.
 */
public class UsuarioDAO {

    private SQLiteDatabase database;
    private String[] columns = { DatabaseHelper.COLUNA_ID, DatabaseHelper.COLUNA_NOME};
    private DatabaseHelper databaseHelper;

    public UsuarioDAO(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException{
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
    }

    public Usuario create(String usuario){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUNA_NOME, usuario);
        long inserId = database.insert(databaseHelper.TABLE_USUARIOS, null, values);
        Cursor cursor = database.query(databaseHelper.TABLE_USUARIOS, columns, databaseHelper.COLUNA_ID + "="+ inserId, null, null, null, null);
        cursor.moveToFirst();
        Usuario newUsuario = new Usuario();
        newUsuario.setNome(cursor.getString(0));
        newUsuario.setSenha(cursor.getString(1));
        cursor.close();
        return newUsuario;
    }

//    public void inserir(View view) {
//        db = helper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(DatabaseHelper.COLUNA_ID, 1);
//        values.put(DatabaseHelper.COLUNA_NOME, "joao");
//        values.put(DatabaseHelper.COLUNA_EMAIL, "joao@gmail.com");
//        values.put(DatabaseHelper.COLUNA_SENHA, "J12345");
//
//        db.insert(TABLE_USUARIOS, null, values);
//        db.close();
//    }
}