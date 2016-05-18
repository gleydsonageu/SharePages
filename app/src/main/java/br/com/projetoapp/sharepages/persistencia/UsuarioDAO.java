package br.com.projetoapp.sharepages.persistencia;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

/**
 * Created by gleydson on 17/05/16.
 */
public class UsuarioDAO {

    private static final String TABLE_USUARIO = "usuario";
    DatabaseHelper helper;
    SQLiteDatabase db;



    public void inserir(View view) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUNA_ID, 1);
        values.put(DatabaseHelper.COLUNA_NOME, "joao");
        values.put(DatabaseHelper.COLUNA_EMAIL, "joao@gmail.com");
        values.put(DatabaseHelper.COLUNA_SENHA, "J12345");

        db.insert(TABLE_USUARIO, null, values);
        db.close();
    }
}