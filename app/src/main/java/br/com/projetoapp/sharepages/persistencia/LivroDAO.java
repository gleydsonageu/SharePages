package br.com.projetoapp.sharepages.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;

public class LivroDAO {

    public static LivroDAO getInstancia() {
        LivroDAO instancia = new LivroDAO();
        return instancia;
    }

    public long inserirLivro(Livro livro) throws SharepagesException {

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.LIVRO_NOME, livro.getNome());
        values.put(DatabaseHelper.LIVRO_AUTOR, livro.getAutor());
        values.put(DatabaseHelper.LIVRO_ID_TEMA, livro.getIdTema());

        long retorno = database.insert(DatabaseHelper.TABLE_LIVRO, null, values);
        database.close();
        return retorno;
    }

    public Livro buscarLivro(String nome, String autor){
        Livro livroEncontrado = null;

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String filtro = DatabaseHelper.LIVRO_NOME + " = ? AND " + DatabaseHelper.LIVRO_AUTOR + " = ? " ;

        Cursor cursor = database.query(DatabaseHelper.TABLE_LIVRO, DatabaseHelper.LIVRO_COLUNAS, filtro,
        new String[]{nome, autor}, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                livroEncontrado = objetoLivro(cursor);
            }
          }
        database.close();
        return livroEncontrado;
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