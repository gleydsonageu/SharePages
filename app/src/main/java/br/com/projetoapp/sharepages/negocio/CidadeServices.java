package br.com.projetoapp.sharepages.negocio;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Cidade;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.CidadeDAO;


public class CidadeServices {

    private static CidadeServices instancia = new CidadeServices();
    private CidadeDAO cidadeDAO;

    private CidadeServices(){
        this.cidadeDAO = CidadeDAO.getInstancia();
    }

    public static CidadeServices getInstancia(){
        return instancia;
    }

    public ArrayList<Cidade> getCidades() throws SharepagesException {

        try {
           return cidadeDAO.getCidades();
        } catch (SharepagesException e) {
            throw new SharepagesException("Houve um erro ao listar cidades, tente novamente");
        }
    }
}