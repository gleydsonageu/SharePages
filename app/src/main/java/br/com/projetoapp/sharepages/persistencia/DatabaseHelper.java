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
    public static final String TABLE_USUARIOS = "usuarios";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_EMAIL = "email";
    public static final String COLUNA_SENHA = "senha";

    private static final String DATABASE_USUARIO = "CREATE TABLE " + TABLE_USUARIOS + "(" +
            COLUNA_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUNA_NOME+" TEXT NOT NULL, "+
            COLUNA_EMAIL+ " TEXT NOT NULL), "+
            COLUNA_SENHA+ " TEXT NOT NULL )";

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_USUARIOS);
        onCreate(db);
    }
}