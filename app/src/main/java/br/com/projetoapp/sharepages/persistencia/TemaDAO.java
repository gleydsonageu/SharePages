package br.com.projetoapp.sharepages.persistencia;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;

/**
 * Esta classe realiza a consulta na Tabela Tema
 */
public class TemaDAO {

    public static TemaDAO getInstancia() {
        TemaDAO instancia = new TemaDAO();
        return instancia;
    }

    /**
     * Usando uma query consulta a tabela tema, carrega uma lista com as tema que contem no banco.
     * @return Lista temas
     * @throws SharepagesException
     */
    public ArrayList<Tema> getTemas() throws SharepagesException{
        Tema tema = null;

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        ArrayList<Tema> listaTemas = new ArrayList<Tema>();

        try {
            Cursor cursor = database.query(DatabaseHelper.TABLE_TEMAS, DatabaseHelper.TEMA_COLUNAS, null, null, null, null, null);

            if(cursor.getCount()> 0){
                cursor.moveToFirst();

                while (!cursor.isAfterLast()){
                    tema = objetoTema(cursor);
                    listaTemas.add(tema);
                    cursor.moveToNext();
                }

            }
            database.close();

        } catch (Exception e) {
            throw new SharepagesException("Houve um erro ao listar temas");
        }
        return listaTemas;
    }

    /**
     *
     * @param cursor cursor para carregar o objeto tema
     * @return tema
     */
    public Tema objetoTema(Cursor cursor){
        Tema tema = null;

        tema = new Tema();
        tema.setId(cursor.getInt(0));
        tema.setNome(cursor.getString(1));

        return tema;
    }
}