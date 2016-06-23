package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.projetoapp.sharepages.dominio.Foto;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.infra.SharepagesException;

public class FotoDAO {

    public static FotoDAO getInstancia() {
        FotoDAO instancia = new FotoDAO();
        return instancia;
    }

    public long inserirFoto(Foto foto) throws SharepagesException {
        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.FOTO_CAMINHO, foto.getCaminho());
        values.put(DatabaseHelper.FOTO_ID_UNIDADELIVRO, foto.getIdUnidadeLivro());


        long retorno = database.insert(DatabaseHelper.TABLE_FOTOS, null, values);
        database.close();
        return retorno;
    }

    public long alterar(Foto foto) throws SharepagesException {
        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.FOTO_CAMINHO, foto.getCaminho());

        String id = String.valueOf(foto.getId());
        String[] whereArgs = new String[]{id};
        long retorno = database.update(DatabaseHelper.TABLE_FOTOS, values, "id=?", whereArgs);
        database.close();
        return retorno;
    }

    public List<Foto> buscarFotosPorIdUnidadeLivro(int id) {
        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        List<Foto> fotos = new ArrayList<Foto>();

        String filtro = DatabaseHelper.FOTO_ID_UNIDADELIVRO + " = ?";

        Cursor cursor = database.query(DatabaseHelper.TABLE_FOTOS, DatabaseHelper.FOTO_COLUNAS, filtro,
                new String[]{String.valueOf(id)}, null, null, null);

        if(cursor.getCount()> 0){
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Foto foto = new Foto(cursor.getString(1));
                foto.setId(cursor.getInt(0));
                foto.setIdUnidadeLivro(cursor.getInt(2));
                fotos.add(foto);
                cursor.moveToNext();
            }
        }
        database.close();

        return fotos;
    }
}
