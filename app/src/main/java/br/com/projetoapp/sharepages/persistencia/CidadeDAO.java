package br.com.projetoapp.sharepages.persistencia;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Cidade;

import br.com.projetoapp.sharepages.gui.TelaInicial;


public class CidadeDAO {

    public DatabaseHelper databaseHelper = new DatabaseHelper(TelaInicial.getContext());

    private static CidadeDAO instancia = new CidadeDAO();

    public static CidadeDAO getInstancia() {
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
                    String idCidade = cursor.getString(0);
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

