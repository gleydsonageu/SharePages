package br.com.projetoapp.sharepages.negocio;


import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.TemaDAO;

public class TemaServices {

    private static TemaServices instancia = new TemaServices();
    private TemaDAO temaDAO;

    private TemaServices(){
        this.temaDAO = TemaDAO.getInstancia();
    }

    public static TemaServices getInstancia(){
        return instancia;
    }

    public ArrayList<Tema> getTemas() throws SharepagesException {

        try {
            return temaDAO.getTemas();
        } catch (SharepagesException e) {
            throw new SharepagesException("Houve um erro ao listar temas, tente novamente");
        }
    }

}
