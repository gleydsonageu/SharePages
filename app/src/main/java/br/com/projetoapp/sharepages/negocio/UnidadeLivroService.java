package br.com.projetoapp.sharepages.negocio;


import android.content.Context;

import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.UnidadeLivroDAO;

public class UnidadeLivroService {

    private static UnidadeLivroService instancia;
    private UnidadeLivroDAO dao;

    public static UnidadeLivroService getInstancia(Context context) {
        if(instancia == null){
            instancia = new UnidadeLivroService();
            instancia.dao = UnidadeLivroDAO.getInstancia(context);
        }
        return instancia;
    }

    public UnidadeLivro inserirUnidadeLivro(UnidadeLivro unidadeLivro) throws SharepagesException{

        try {
         int  idUnidadeLivro = (int) dao.inserirUnidadeLivro(unidadeLivro);
            unidadeLivro.setId(idUnidadeLivro);
        } catch (SharepagesException e) {
            throw new SharepagesException("Houve um erro inserir livro");
        }
        return unidadeLivro;

    }
}
