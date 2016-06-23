package br.com.projetoapp.sharepages.persistencia;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;

public class TemaDAO {

    public static TemaDAO getInstancia() {
        TemaDAO instancia = new TemaDAO();
        return instancia;
    }

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

    public Tema objetoTema(Cursor cursor){
        Tema tema = null;

        tema = new Tema();
        tema.setId(cursor.getInt(0));
        tema.setNome(cursor.getString(1));

        return tema;
    }
}