package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.SharepagesException;

public class UnidadeLivroDAO {

    public DatabaseHelper databaseHelper;
    private static UnidadeLivroDAO instancia;

    public static UnidadeLivroDAO getInstancia(Context context) {
            instancia = new UnidadeLivroDAO();
            instancia.databaseHelper = new DatabaseHelper(context);

        return instancia;
    }

//    public static UnidadeLivroDAO getInstancia(Context context) {
//        if(instancia == null){
//            instancia = new UnidadeLivroDAO();
//            instancia.databaseHelper = new DatabaseHelper(context);
//        }
//        return instancia;
//    }

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


            Log.i("SCRIPT", " UnidadeLivro cadastrado " + unidadeLivro.getEditora());

            long retorno = database.insert(DatabaseHelper.TABLE_UNIDADELIVROS, null, values);
            database.close();
            return retorno;
        }
    }
}
