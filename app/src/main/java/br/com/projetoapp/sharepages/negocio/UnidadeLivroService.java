package br.com.projetoapp.sharepages.negocio;


import android.content.Context;

import br.com.projetoapp.sharepages.persistencia.UnidadeLivroDAO;

public class UnidadeLivroService {

    private static UnidadeLivroService instancia;
    private UnidadeLivroDAO dao;

    public static UnidadeLivroService getInstancia(Context context) {
        if(instancia == null){
            instancia = new UnidadeLivroService();
            instancia.dao = UnidadeLivroDAO.getInstancia(context);
        }
        return instancia;
    }
}
