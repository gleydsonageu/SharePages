package br.com.projetoapp.sharepages.negocio;


import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Disponibilidade;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.DisponibilidadeDAO;

/**
 * DisponibilidadeServices trata solicitações na DisponibilidadeDAO e ações relacionadas a disponibilidade.
 */
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

    /**
     * Método que solicita uma consulta de todas as disponibilidades no banco de dados
     * @return uma lista com as disponibilidades
     * @throws SharepagesException
     */
    public ArrayList<Disponibilidade> getDisponibilidades() throws SharepagesException {

        try {
            return disponibilidadeDAO.getDisponibilidades();
        } catch (SharepagesException e) {
            throw new SharepagesException("Houve um erro ao listar disponibilidades, tente novamente");
        }
    }
}
