package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.projetoapp.sharepages.dominio.Foto;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
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

        Log.i("SCRIPT", " caminho nome " + foto.getCaminho());


        long retorno = database.insert(DatabaseHelper.TABLE_FOTOS, null, values);
        database.close();
        return retorno;
    }


}
