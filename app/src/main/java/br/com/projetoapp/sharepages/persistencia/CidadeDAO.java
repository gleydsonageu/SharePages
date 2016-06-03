package br.com.projetoapp.sharepages.persistencia;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Cidade;


public class CidadeDAO {

    public DatabaseHelper databaseHelper;
    private static CidadeDAO instancia;

    public static CidadeDAO getInstancia(Context context) {
        if (instancia == null){
            instancia = new CidadeDAO();
            instancia.databaseHelper = new DatabaseHelper(context);
        }
        return instancia;
    }

    public ArrayList<Cidade> pegarCidades() throws Exception{

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        try {
            ArrayList<Cidade> listaCidades = new ArrayList<Cidade>();

                Cursor cursor = database.query(DatabaseHelper.TABLE_CIDADES, DatabaseHelper.CIDADE_COLUNAS, null, null, null, null, null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Cidade cidade = new Cidade();
                    int idCidade = cursor.getInt(0);
                    String nomeCidade = cursor.getString(1);
                    cidade.setId(idCidade);
                    cidade.setNome(nomeCidade);
                    listaCidades.add(cidade);
                    cursor.moveToNext();
                }
            Log.d("AQUI", listaCidades.toString());

            return listaCidades;
        } catch (Exception e) {
            throw e;
        }
    }

}

