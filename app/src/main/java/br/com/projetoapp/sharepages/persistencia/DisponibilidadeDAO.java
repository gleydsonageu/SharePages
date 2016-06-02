package br.com.projetoapp.sharepages.persistencia;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Cidade;
import br.com.projetoapp.sharepages.dominio.Disponibilidade;

public class DisponibilidadeDAO {

    public DatabaseHelper databaseHelper;
    private static DisponibilidadeDAO instancia;

    public static DisponibilidadeDAO getInstancia (Context context) {
        if (instancia == null){
            instancia = new DisponibilidadeDAO();
            instancia.databaseHelper = new DatabaseHelper(context);
        }
        return instancia;

    }

    public ArrayList<Disponibilidade> pegarDisponibilidades() throws Exception{

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        try {
            ArrayList<Disponibilidade> listaDisponibilidade = new ArrayList<Disponibilidade>();

            Cursor cursor = database.query(DatabaseHelper.TABLE_DISPONIBILIDADES, DatabaseHelper.DISPONIBILIDADE_COLUNAS, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Disponibilidade disponibilidade = new Disponibilidade();
                int idCidade = cursor.getInt(0);
                String nomeCidade = cursor.getString(1);
                disponibilidade.setId(idCidade);
                disponibilidade.setNome(nomeCidade);
                listaDisponibilidade.add(disponibilidade);
                cursor.moveToNext();
            }
            Log.d("AQUI", listaDisponibilidade.toString());

            return listaDisponibilidade;
        } catch (Exception e) {
            throw e;
        }
    }
}
