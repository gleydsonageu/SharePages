package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.infra.SharepagesException;

public class LivroDAO {

    public DatabaseHelper databaseHelper;
    private static LivroDAO instancia;

    public static LivroDAO getInstancia(Context context) {
        if(instancia == null){
            instancia = new LivroDAO();
            instancia.databaseHelper = new DatabaseHelper(context);
        }
        return instancia;
    }

    public long inserirLivro(Livro livro) throws SharepagesException {

        try (SQLiteDatabase database = databaseHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.LIVRO_NOME, livro.getNome());
            values.put(DatabaseHelper.LIVRO_AUTOR, livro.getAutor());


            Log.i("SCRIPT", " livro nome e autor cadastrados " + livro.getNome());

            return database.insert(DatabaseHelper.TABLE_LIVRO, null, values);
        }
    }
}
