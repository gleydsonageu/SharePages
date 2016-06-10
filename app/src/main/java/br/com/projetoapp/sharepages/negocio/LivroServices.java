package br.com.projetoapp.sharepages.negocio;


import android.content.Context;

import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.LivroDAO;

public class LivroServices {

    private static LivroServices instancia;
    private LivroDAO dao;

    public static LivroServices getInstancia(Context context) {
        if(instancia == null){
            instancia = new LivroServices();
            instancia.dao = LivroDAO.getInstancia(context);
        }
        return instancia;
    }

    public Livro inserirLivro(Livro livro) throws SharepagesException {
        Livro livroEncontrado;

        try {
            livroEncontrado = dao.buscarLivro(livro.getNome(), livro.getAutor());
        } catch (Exception e){
            e.printStackTrace();
            throw new SharepagesException("Erro");
        }

        if (livroEncontrado != null){

            return livroEncontrado;

        }else {
           int idLivro = (int) dao.inserirLivro(livro);
            livro.setId(idLivro);

            return livro;
        }
    }

}
