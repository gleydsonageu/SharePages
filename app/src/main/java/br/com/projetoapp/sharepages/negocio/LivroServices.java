package br.com.projetoapp.sharepages.negocio;


import android.content.Context;

import br.com.projetoapp.sharepages.persistencia.LivroDAO;

public class LivroServices {

    private static LivroServices instancia;
    private LivroDAO dao;

    public static LivroServices getInstancia(Context context) {
        if(instancia == null){
            instancia = new LivroServices();
            instancia.dao = LivroDAO.getInstancia(context);
        }
        return instancia;
    }
}
