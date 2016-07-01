package br.com.projetoapp.sharepages.negocio;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Cidade;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.CidadeDAO;

/**
 * CidadeServices trata solicitações na CidadeDAO e ações relacionadas a cidade.
 */
public class CidadeServices {

    private static CidadeServices instancia = new CidadeServices();
    private CidadeDAO cidadeDAO;

    private CidadeServices(){
        this.cidadeDAO = CidadeDAO.getInstancia();
    }

    public static CidadeServices getInstancia(){
        return instancia;
    }

    /**
     * Método que solicita a consulta no banco para pegar todas as cidades que existem nele
     * @return uma lista com todas as cidades encontradas no banco de dados
     * @throws SharepagesException
     */
    public ArrayList<Cidade> getCidades() throws SharepagesException {

        try {
           return cidadeDAO.getCidades();
        } catch (SharepagesException e) {
            throw new SharepagesException("Houve um erro ao listar cidades, tente novamente");
        }
    }
}