package br.com.projetoapp.sharepages.negocio;


import android.content.Context;

import br.com.projetoapp.sharepages.dominio.Foto;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.FotoDAO;

public class FotoServices {

    private static FotoServices instancia;
    private FotoDAO dao;

    public static FotoServices getInstancia(Context context) {
        if(instancia == null){
            instancia = new FotoServices();
            instancia.dao = FotoDAO.getInstancia(context);
        }
        return instancia;
    }

    public Foto inserirFoto(Foto foto) throws SharepagesException{

        try {
            int  idFoto = (int) dao.inserirFoto(foto);
            foto.setId(idFoto);
        } catch (SharepagesException e) {
            throw new SharepagesException("Houve um erro inserir foto");
        }
        return foto;

    }
}
