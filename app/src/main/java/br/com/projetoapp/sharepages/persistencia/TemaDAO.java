package br.com.projetoapp.sharepages.persistencia;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.infra.SharepagesException;

public class TemaDAO {

    DatabaseHelper databaseHelper;


    public static TemaDAO getInstancia(Context context) {
        TemaDAO instancia = new TemaDAO();
        instancia.databaseHelper = new DatabaseHelper(context);
        return instancia;
    }

    public ArrayList<Tema> pegarTemas() throws SharepagesException{
        Tema tema = null;
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        try {
            ArrayList<Tema> listaTemas = new ArrayList<Tema>();

            Cursor cursor = database.query(DatabaseHelper.TABLE_TEMAS, DatabaseHelper.TEMA_COLUNAS, null, null, null, null, null);

            if(cursor.getCount()> 0){
                cursor.moveToFirst();

                while (!cursor.isAfterLast()){
                    tema = objetoTema(cursor);
                    listaTemas.add(tema);
                    cursor.moveToNext();
                }

            }
//            while (!cursor.isAfterLast()) {
//                Tema tema = new Tema();
//                int idTema = cursor.getInt(0);
//                String nomeTema = cursor.getString(1);
//                tema.setId(idTema);
//                tema.setNome(nomeTema);
//                listaTemas.add(tema);
//                cursor.moveToNext();
//            }

            Log.d("AQUI", listaTemas.toString());

            database.close();
            return listaTemas;
        } catch (Exception e) {
            throw new SharepagesException("Houve um erro ao listar temas");
        }

    }
    public Tema objetoTema(Cursor cursor){
        Tema tema = null;

        tema = new Tema();
        tema.setId(cursor.getInt(0));
        tema.setNome(cursor.getString(1));

        return tema;
    }

}
