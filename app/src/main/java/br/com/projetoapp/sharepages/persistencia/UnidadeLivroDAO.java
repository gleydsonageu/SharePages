package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.projetoapp.sharepages.dominio.Disponibilidade;
import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;

public class UnidadeLivroDAO {

    public static UnidadeLivroDAO getInstacia(){
        UnidadeLivroDAO instancia = new UnidadeLivroDAO();
        return instancia;
    }

    public long setUnidadeLivro(UnidadeLivro unidadeLivro) throws SharepagesException {

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
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

            long retorno = database.insert(DatabaseHelper.TABLE_UNIDADELIVROS, null, values);
            database.close();
            return retorno;
        }
    }

    public long alterar(UnidadeLivro unidadeLivro) throws SharepagesException {
        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.UNIDADELIVRO_DESCRICAO, unidadeLivro.getDescricao());
        values.put(DatabaseHelper.UNIDADELIVRO_ID_DISPONIBILIDADE, unidadeLivro.getIdDisponibilidade());

        String id = String.valueOf(unidadeLivro.getId());
        String[] whereArgs = new String[]{id};
        long retorno = database.update(DatabaseHelper.TABLE_UNIDADELIVROS, values, "id = ?", whereArgs);
        database.close();
        return retorno;
    }

    public List<UnidadeLivro> getLivroPorIdUsuario(int id){
        UnidadeLivro unidadeLivro = null;

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
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
                +DatabaseHelper.TABLE_DISPONIBILIDADES+"."+DatabaseHelper.DISPONIBILIDADE_NOME
                +" FROM "+DatabaseHelper.TABLE_UNIDADELIVROS +" INNER JOIN "+ DatabaseHelper.TABLE_LIVRO
                +" ON ("+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_LIVRO+ " = " +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_TEMAS
                +" ON (" +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID_TEMA+ " = " +DatabaseHelper.TABLE_TEMAS +"."+ DatabaseHelper.TEMAS_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_DISPONIBILIDADES
                +" ON ("+ DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_DISPONIBILIDADE+ " = " +DatabaseHelper.TABLE_DISPONIBILIDADES+ "." +DatabaseHelper.DISPONIBILIDADE_ID
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

    public UnidadeLivro getPorId(int id){
        UnidadeLivro unidadeLivro = null;

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
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
                +DatabaseHelper.TABLE_DISPONIBILIDADES+"."+DatabaseHelper.DISPONIBILIDADE_NOME
                +" FROM "+DatabaseHelper.TABLE_UNIDADELIVROS +" INNER JOIN "+ DatabaseHelper.TABLE_LIVRO
                +" ON ("+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_LIVRO+ " = " +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_TEMAS
                +" ON (" +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID_TEMA+ " = " +DatabaseHelper.TABLE_TEMAS +"."+ DatabaseHelper.TEMAS_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_DISPONIBILIDADES
                +" ON ("+ DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_DISPONIBILIDADE+ " = " +DatabaseHelper.TABLE_DISPONIBILIDADES+ "." +DatabaseHelper.DISPONIBILIDADE_ID
                +") WHERE "+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID+ " = ?;", new String[]{String.valueOf(id)});

        cursor.moveToFirst();
        unidadeLivro = carregandoUnidadeLivro(cursor);
        database.close();
        return unidadeLivro;
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

        unidLivro.setFotos(FotoDAO.getInstancia().buscarFotosPorIdUnidadeLivro(unidLivro.getId()));

        unidLivro.setLivro(livro);
        livro.setTema(tema);
        unidLivro.setDisponibilidade(disponibilidade);
        return unidLivro;
    }

    public boolean alterarSituacao(UnidadeLivro unidadeLivro) {
        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.UNIDADELIVRO_SITUACAO, unidadeLivro.getSituacao().getNome());

        String id = String.valueOf(unidadeLivro.getId());
        String[] whereArgs = new String[]{id};
        long retorno = database.update(DatabaseHelper.TABLE_UNIDADELIVROS, values, "id = ?", whereArgs);
        database.close();

        return retorno == 1;
    }

}