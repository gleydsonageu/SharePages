package br.com.projetoapp.sharepages.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gleydson on 17/05/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DATABASE = "meubanco";

    //Criando a tabela usuarioDAO
    public static final String COLUNA_ID = "id";
    public static final String TABLE_USUARIO = "usuario";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_EMAIL = "email";
    public static final String COLUNA_SENHA = "senha";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USUARIO + "(" + COLUNA_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUNA_NOME+" TEXT NOT NULL, "+
                COLUNA_EMAIL+ " TEXT NOT NULL), "+
                COLUNA_SENHA+ " TEXT NOT NULL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion){
            //atualiza o banco
        }
    }
}