package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.projetoapp.sharepages.dominio.Disponibilidade;
import br.com.projetoapp.sharepages.dominio.Foto;
import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.SharepagesException;

public class UnidadeLivroDAO {

    public DatabaseHelper databaseHelper;


    public static UnidadeLivroDAO getInstancia(Context context) {
        UnidadeLivroDAO instancia = new UnidadeLivroDAO();
        instancia.databaseHelper = new DatabaseHelper(context);
        return instancia;
    }

    public long inserirUnidadeLivro(UnidadeLivro unidadeLivro) throws SharepagesException {

        try (SQLiteDatabase database = databaseHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.UNIDADELIVRO_DESCRICAO, unidadeLivro.getDescricao());
            values.put(DatabaseHelper.UNIDADELIVRO_IDIOMA, unidadeLivro.getIdioma());
            values.put(DatabaseHelper.UNIDADELIVRO_EDICAO, unidadeLivro.getEdicao());
            values.put(DatabaseHelper.UNIDADELIVRO_NUMEROPAGINAS, unidadeLivro.getNumeroPaginas());
            values.put(DatabaseHelper.UNIDADELIVRO_EDITORA, unidadeLivro.getEditora());
            values.put(DatabaseHelper.UNIDADELIVRO_ID_LIVRO, unidadeLivro.getIdLivro());
            values.put(DatabaseHelper.UNIDADELIVRO_ID_USUARIO, unidadeLivro.getIdUsuario());
            values.put(DatabaseHelper.UNIDADELIVRO_ID_DISPONIBILIDADE, unidadeLivro.getIdDisponibilidade());

            //Log.i("SCRIPT", " UnidadeLivro cadastrado " + unidadeLivro.getEditora());

            long retorno = database.insert(DatabaseHelper.TABLE_UNIDADELIVROS, null, values);
            database.close();
            return retorno;
        }
    }

    public List<UnidadeLivro> buscarLivroPorIdUsuario (int id){
        UnidadeLivro unidadeLivro = null;

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        List<UnidadeLivro> listUnidadeLivro = new ArrayList<UnidadeLivro>();

        Cursor cursor = database.rawQuery(" SELECT " + DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID+ ", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_DESCRICAO+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_IDIOMA+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_EDICAO+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_NUMEROPAGINAS+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_EDITORA+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_LIVRO+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_DISPONIBILIDADE+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_USUARIO+", "
                +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID+", "
                +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_NOME+", "
                +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_AUTOR+", "
                +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID_TEMA+", "
                +DatabaseHelper.TABLE_TEMAS+"."+DatabaseHelper.TEMAS_ID+", "
                +DatabaseHelper.TABLE_TEMAS+"."+DatabaseHelper.TEMAS_NOME+", "
                +DatabaseHelper.TABLE_DISPONIBILIDADES+"."+DatabaseHelper.DISPONIBILIDADE_ID+", "
                +DatabaseHelper.TABLE_DISPONIBILIDADES+"."+DatabaseHelper.DISPONIBILIDADE_NOME+", "
                +DatabaseHelper.TABLE_FOTOS+"."+DatabaseHelper.FOTO_CAMINHO
                +" FROM "+DatabaseHelper.TABLE_UNIDADELIVROS +" INNER JOIN "+ DatabaseHelper.TABLE_LIVRO
                +" ON ("+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_LIVRO+ " = " +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_TEMAS
                +" ON (" +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID_TEMA+ " = " +DatabaseHelper.TABLE_TEMAS +"."+ DatabaseHelper.TEMAS_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_DISPONIBILIDADES
                +" ON ("+ DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_DISPONIBILIDADE+ " = " +DatabaseHelper.TABLE_DISPONIBILIDADES+ "." +DatabaseHelper.DISPONIBILIDADE_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_FOTOS
                +" ON ("+DatabaseHelper.TABLE_FOTOS+"."+DatabaseHelper.FOTO_ID_UNIDADELIVRO+ " = " +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID
                +") WHERE "+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_USUARIO+ " = ?;", new String[]{String.valueOf(id)});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            unidadeLivro = carregandoUnidadeLivro(cursor);
            listUnidadeLivro.add(unidadeLivro);
            Log.i("SCRIPT", "Montei a unidadelivro " + unidadeLivro.toString());
            cursor.moveToNext();
        }
        database.close();
        return listUnidadeLivro;
    }

    public UnidadeLivro carregandoUnidadeLivro(Cursor cursor){
        UnidadeLivro unidLivro = null;

        unidLivro = new UnidadeLivro();
        unidLivro.setId(cursor.getInt(0));
        unidLivro.setDescricao(cursor.getString(1));
        unidLivro.setIdioma(cursor.getString(2));
        unidLivro.setEdicao(cursor.getString(3));
        unidLivro.setNumeroPaginas(cursor.getInt(4));
        unidLivro.setEditora(cursor.getString(5));
        unidLivro.setIdLivro(cursor.getInt(6));
        unidLivro.setIdDisponibilidade(cursor.getInt(7));
        unidLivro.setIdUsuario(cursor.getInt(8));
        Livro livro = new Livro();
        livro.setId(cursor.getInt(9));
        livro.setNome(cursor.getString(10));
        livro.setAutor(cursor.getString(11));
        livro.setIdTema(cursor.getInt(12));
        Tema  tema = new Tema();
        tema.setId(cursor.getInt(13));
        tema.setNome(cursor.getString(14));
        Disponibilidade disponibilidade = new Disponibilidade();
        disponibilidade.setId(cursor.getInt(15));
        disponibilidade.setNome(cursor.getString(16));
        Foto foto = new Foto(cursor.getString(17));



        unidLivro.setLivro(livro);
        livro.setTema(tema);
        unidLivro.setDisponibilidade(disponibilidade);
        unidLivro.setFoto(foto);

        return unidLivro;
    }

    public UnidadeLivro buscarPorId (int id){
        UnidadeLivro unidadeLivro = null;

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery(" SELECT " + DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID+ ", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_DESCRICAO+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_IDIOMA+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_EDICAO+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_NUMEROPAGINAS+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_EDITORA+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_LIVRO+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_DISPONIBILIDADE+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_USUARIO+", "
                +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID+", "
                +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_NOME+", "
                +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_AUTOR+", "
                +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID_TEMA+", "
                +DatabaseHelper.TABLE_TEMAS+"."+DatabaseHelper.TEMAS_ID+", "
                +DatabaseHelper.TABLE_TEMAS+"."+DatabaseHelper.TEMAS_NOME+", "
                +DatabaseHelper.TABLE_DISPONIBILIDADES+"."+DatabaseHelper.DISPONIBILIDADE_ID+", "
                +DatabaseHelper.TABLE_DISPONIBILIDADES+"."+DatabaseHelper.DISPONIBILIDADE_NOME+", "
                +DatabaseHelper.TABLE_FOTOS+"."+DatabaseHelper.FOTO_CAMINHO
                +" FROM "+DatabaseHelper.TABLE_UNIDADELIVROS +" INNER JOIN "+ DatabaseHelper.TABLE_LIVRO
                +" ON ("+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_LIVRO+ " = " +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_TEMAS
                +" ON (" +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID_TEMA+ " = " +DatabaseHelper.TABLE_TEMAS +"."+ DatabaseHelper.TEMAS_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_DISPONIBILIDADES
                +" ON ("+ DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_DISPONIBILIDADE+ " = " +DatabaseHelper.TABLE_DISPONIBILIDADES+ "." +DatabaseHelper.DISPONIBILIDADE_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_FOTOS
                +" ON ("+DatabaseHelper.TABLE_FOTOS+"."+DatabaseHelper.FOTO_ID_UNIDADELIVRO+ " = " +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID
                +") WHERE "+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID+ " = ?;", new String[]{String.valueOf(id)});

        cursor.moveToFirst();
        unidadeLivro = carregandoUnidadeLivro(cursor);
        database.close();
        return unidadeLivro;
    }

}