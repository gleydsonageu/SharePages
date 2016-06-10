package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.projetoapp.sharepages.dominio.Foto;
import br.com.projetoapp.sharepages.infra.SharepagesException;

public class FotoDAO {

    public DatabaseHelper databaseHelper;


    public static FotoDAO getInstancia(Context context) {
        FotoDAO instancia = new FotoDAO();
        instancia.databaseHelper = new DatabaseHelper(context);
        return instancia;
    }

    public long inserirFoto(Foto foto) throws SharepagesException {

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.FOTO_CAMINHO, foto.getCaminho());
        values.put(DatabaseHelper.FOTO_ID_UNIDADELIVRO, foto.getIdUnidadeLivro());

        Log.i("SCRIPT", " caminho nome " + foto.getCaminho());


        long retorno = database.insert(DatabaseHelper.TABLE_FOTOS, null, values);
        database.close();
        return retorno;
    }


}
