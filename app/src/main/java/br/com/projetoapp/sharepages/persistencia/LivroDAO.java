package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
            values.put(DatabaseHelper.LIVRO_ID_TEMA, livro.getIdTema());


            Log.i("SCRIPT", " livro nome " + livro.getNome());

            return database.insert(DatabaseHelper.TABLE_LIVRO, null, values);
        }
    }

    public Livro buscarLivro(String nome, String autor){
        Livro livroEncontrado = null;

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String filtro = DatabaseHelper.LIVRO_NOME + " = ? AND " + DatabaseHelper.LIVRO_AUTOR + " = ? " ;
        Cursor cursor = database.query(DatabaseHelper.TABLE_LIVRO, DatabaseHelper.LIVRO_COLUNAS, filtro,
                new String[]{nome, autor}, null, null, null);

        if (cursor.moveToFirst()){
            int idLivro = cursor.getInt(0);
            String nomeLivro = cursor.getString(1);
            String nomeAutor = cursor.getString(2);
            livroEncontrado = new Livro();
            livroEncontrado.setId(idLivro);
            livroEncontrado.setNome(nomeLivro);
            livroEncontrado.setAutor(nomeAutor);

            cursor.moveToNext();
        }
        database.close();
        return livroEncontrado;
    }


}
