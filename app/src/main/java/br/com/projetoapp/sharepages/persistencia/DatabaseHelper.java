package br.com.projetoapp.sharepages.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 4;
    public static final String DATABASE = "meubanco.db";

    //Criando a tabela usuarioDAO
    public static final String TABLE_USUARIOS = "usuarios";

    public static final String USUARIO_ID = "id";
    public static final String USUARIO_NOME = "nome";
    public static final String USUARIO_EMAIL = "email";
    public static final String USUARIO_SENHA = "senha";
    public static final String[] USUARIO_COLUNAS = { USUARIO_ID, USUARIO_NOME, USUARIO_EMAIL, USUARIO_SENHA };

    private static final String DATABASE_USUARIO = "CREATE TABLE " + TABLE_USUARIOS + "(" +
            USUARIO_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            USUARIO_NOME +" TEXT NOT NULL, "+
            USUARIO_EMAIL + " TEXT NOT NULL UNIQUE, "+
            USUARIO_SENHA + " TEXT NOT NULL)";

    private static final String DATABASE_SEED = "INSERT INTO " + TABLE_USUARIOS
            + " VALUES (NULL,'Joao','joao@gmail.com','123456')";


    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_USUARIO);
        database.execSQL(DATABASE_SEED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USUARIOS);
        onCreate(db);
    }
}