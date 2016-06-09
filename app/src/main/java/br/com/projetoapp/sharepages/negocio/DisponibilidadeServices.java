package br.com.projetoapp.sharepages.negocio;


import android.content.Context;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Disponibilidade;
import br.com.projetoapp.sharepages.persistencia.DisponibilidadeDAO;

public class DisponibilidadeServices {

    private static DisponibilidadeServices instancia;
    private DisponibilidadeDAO daoDisponibilidade;

    public static DisponibilidadeServices getInstancia(Context context){
        if(instancia == null){
            instancia = new DisponibilidadeServices();
            instancia.daoDisponibilidade = DisponibilidadeDAO.getInstancia(context);
        }
        return instancia;
    }

    public ArrayList<Disponibilidade> pegarDisponibilidades() throws Exception {

        try {
            return daoDisponibilidade.pegarDisponibilidades();
        } catch (Exception e) {

            throw new Exception("Houve um erro ao listar disponibilidades, tente novamente");
        }
    }
}
