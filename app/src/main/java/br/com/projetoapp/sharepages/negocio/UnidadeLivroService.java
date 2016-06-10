package br.com.projetoapp.sharepages.negocio;


import android.content.Context;

import java.util.List;

import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.UnidadeLivroDAO;

public class UnidadeLivroService {


    private UnidadeLivroDAO dao;

    public static UnidadeLivroService getInstancia(Context context) {
        UnidadeLivroService  instancia = new UnidadeLivroService();
        instancia.dao = UnidadeLivroDAO.getInstancia(context);

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
    public List<UnidadeLivro> buscarLivroPorUsuario(int id){
       // Log.i("SCRIPT","buscarlivroPorUsuario ======= "+id);
        return dao.buscarLivroPorIdUsuario(id);
    }
}
