package br.com.projetoapp.sharepages.negocio;


import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.LivroDAO;

public class LivroServices {

    private static LivroServices instancia = new LivroServices();
    private LivroDAO livroDAO;

    private LivroServices(){
        this.livroDAO = LivroDAO.getInstancia();
    }

    public static LivroServices getInstancia() {
        return instancia;
    }

    public Livro inserirLivro(Livro livro) throws SharepagesException {
        Livro livroEncontrado;

        try {
            livroEncontrado = livroDAO.buscarLivro(livro.getNome(), livro.getAutor());
        } catch (Exception e){
            e.printStackTrace();
            throw new SharepagesException("Erro");
        }

        if (livroEncontrado != null){

            return livroEncontrado;

        }else {
           int idLivro = (int) livroDAO.inserirLivro(livro);
            livro.setId(idLivro);

            return livro;
        }
    }

}
