package br.com.projetoapp.sharepages.negocio;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Cidade;
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

    public ArrayList<Cidade> pegarCidades() throws Exception {

        try {
           return cidadeDAO.pegarCidades();
        } catch (Exception e) {
            throw new Exception("Houve um erro ao listar cidades, tente novamente");
        }
    }
}
