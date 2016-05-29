package br.com.projetoapp.sharepages.negocio;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Cidade;
import br.com.projetoapp.sharepages.persistencia.CidadeDAO;


public class CidadeServices {

    private static CidadeServices instancia = new CidadeServices();

    public static CidadeServices getInstancia() {
        return instancia;
    }

    private CidadeDAO daoCidade = CidadeDAO.getInstancia();

    public ArrayList<Cidade> pegarCidades() throws Exception {

        try {
           return daoCidade.pegarCidades();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Houve um erro ao listar cidades, tente novamente");
        }
    }
}
