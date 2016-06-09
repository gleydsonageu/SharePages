package br.com.projetoapp.sharepages.persistencia;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Disponibilidade;
import br.com.projetoapp.sharepages.infra.SharepagesException;

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

    public ArrayList<Disponibilidade> pegarDisponibilidades() throws SharepagesException{
        Disponibilidade disponibilidade = null;
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        try {
            ArrayList<Disponibilidade> listaDisponibilidade = new ArrayList<Disponibilidade>();

            Cursor cursor = database.query(DatabaseHelper.TABLE_DISPONIBILIDADES, DatabaseHelper.DISPONIBILIDADE_COLUNAS, null, null, null, null, null);

            if(cursor.getCount()> 0){
                cursor.moveToFirst();

                while (!cursor.isAfterLast()){
                    disponibilidade = objetoDisponibilidade(cursor);
                    listaDisponibilidade.add(disponibilidade);
                        cursor.moveToNext();
                }
            }
//            while (!cursor.isAfterLast()) {
//                Disponibilidade disponibilidade = new Disponibilidade();
//                int idDisponibilidade = cursor.getInt(0);
//                String nomeDisponibilidade = cursor.getString(1);
//                disponibilidade.setId(idDisponibilidade);
//                disponibilidade.setNome(nomeDisponibilidade);
//                listaDisponibilidade.add(disponibilidade);
//                cursor.moveToNext();
//            }

            Log.d("AQUI", listaDisponibilidade.toString());

            database.close();
            return listaDisponibilidade;
        } catch (Exception e) {
            throw new SharepagesException("Houve um erro ao listar disponibilidade");
        }
    }

    public Disponibilidade objetoDisponibilidade(Cursor cursor){
        Disponibilidade disponibilidade = null;

        disponibilidade = new Disponibilidade();
        disponibilidade.setId(cursor.getInt(0));
        disponibilidade.setNome(cursor.getString(1));

        return disponibilidade;
    }
}