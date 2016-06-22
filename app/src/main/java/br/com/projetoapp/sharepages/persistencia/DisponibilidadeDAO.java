package br.com.projetoapp.sharepages.persistencia;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Disponibilidade;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;

public class DisponibilidadeDAO {

    public static DisponibilidadeDAO getInstancia () {
        DisponibilidadeDAO instancia = new DisponibilidadeDAO();
        return instancia;
    }

    public ArrayList<Disponibilidade> getDisponibilidades() throws SharepagesException{
        Disponibilidade disponibilidade = null;

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
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