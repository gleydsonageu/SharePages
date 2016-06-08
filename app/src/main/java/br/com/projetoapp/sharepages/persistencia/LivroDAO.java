package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.LIVRO_NOME, livro.getNome());
            values.put(DatabaseHelper.LIVRO_AUTOR, livro.getAutor());
            values.put(DatabaseHelper.LIVRO_ID_TEMA, livro.getIdTema());


            Log.i("SCRIPT", " livro nome " + livro.getNome());


            long retorno = database.insert(DatabaseHelper.TABLE_LIVRO, null, values);
            database.close();
            return retorno;
    }

    public Livro buscarLivro(String nome, String autor){
        Livro livroEncontrado = null;

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String filtro = DatabaseHelper.LIVRO_NOME + " = ? AND " + DatabaseHelper.LIVRO_AUTOR + " = ? " ;
        Cursor cursor = database.query(DatabaseHelper.TABLE_LIVRO, DatabaseHelper.LIVRO_COLUNAS, filtro,
                new String[]{nome, autor}, null, null, null);

          if(cursor.getCount()> 0){
              while (cursor.moveToNext()){
                  livroEncontrado = objetoLivro(cursor);
              }
          }
//        if (cursor.moveToFirst()){
//            int idLivro = cursor.getInt(0);
//            String nomeLivro = cursor.getString(1);
//            String nomeAutor = cursor.getString(2);
//            livroEncontrado = new Livro();
//            livroEncontrado.setId(idLivro);
//            livroEncontrado.setNome(nomeLivro);
//            livroEncontrado.setAutor(nomeAutor);
//
//            cursor.moveToNext();
//        }
        database.close();
        return livroEncontrado;
    }

    public List<Livro> livroList(String nome){
        Livro livro = null;

        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        List<Livro> listaLivros = new ArrayList<Livro>();

        String filtro = DatabaseHelper.LIVRO_NOME + "= ? ";
        Cursor cursor = database.query(DatabaseHelper.TABLE_LIVRO,DatabaseHelper.LIVRO_COLUNAS, filtro,
                new String[]{"%"+nome+"%"}, null, null, null);

        if(cursor.getCount()> 0){
            while (cursor.moveToNext()){
                livro = objetoLivro(cursor);
                listaLivros.add(livro);
            }
        }
        database.close();
        return listaLivros;
    }

    public Livro objetoLivro(Cursor cursor){
        Livro livro = null;

        if(cursor.getCount()> 0){
            livro = new Livro();
            livro.setId(cursor.getInt(0));
            livro.setNome(cursor.getString(1));
            livro.setAutor(cursor.getString(2));
        }
        return livro;
    }
}
