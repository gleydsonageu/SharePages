package br.com.projetoapp.sharepages.negocio;


import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Disponibilidade;
import br.com.projetoapp.sharepages.persistencia.DisponibilidadeDAO;

public class DisponibilidadeServices {

    private static DisponibilidadeServices instancia = new DisponibilidadeServices();
    private DisponibilidadeDAO disponibilidadeDAO;

    public DisponibilidadeServices (){
        this.disponibilidadeDAO = DisponibilidadeDAO.getInstancia();
    }

    public static DisponibilidadeServices getInstancia(){
        DisponibilidadeServices  instancia = new DisponibilidadeServices();
        return instancia;
    }

    public ArrayList<Disponibilidade> pegarDisponibilidades() throws Exception {

        try {
            return disponibilidadeDAO.pegarDisponibilidades();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Houve um erro ao listar disponibilidades, tente novamente");
        }
    }
}
