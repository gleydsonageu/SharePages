package br.com.projetoapp.sharepages.negocio;


import android.content.Context;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.persistencia.TemaDAO;

public class TemaServices {

    private static TemaServices instancia;
    private TemaDAO daoTema;

    public static TemaServices getInstancia(Context context){
        if(instancia == null){
            instancia = new TemaServices();
            instancia.daoTema = TemaDAO.getInstancia(context);
        }
        return instancia;
    }

    public ArrayList<Tema> pegarCidades() throws Exception {

        try {
            return daoTema.pegarTemas();
        } catch (Exception e) {
            throw new Exception("Houve um erro ao listar temas, tente novamente");
        }
    }
}
