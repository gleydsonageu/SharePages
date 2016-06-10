package br.com.projetoapp.sharepages.negocio;

import android.content.Context;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Cidade;
import br.com.projetoapp.sharepages.persistencia.CidadeDAO;


public class CidadeServices {


    private CidadeDAO daoCidade;

    public static CidadeServices getInstancia(Context context){
        CidadeServices  instancia = new CidadeServices();
        instancia.daoCidade = CidadeDAO.getInstancia(context);

        return instancia;
    }

    public ArrayList<Cidade> pegarCidades() throws Exception {

        try {
           return daoCidade.pegarCidades();
        } catch (Exception e) {
            throw new Exception("Houve um erro ao listar cidades, tente novamente");
        }
    }
}
