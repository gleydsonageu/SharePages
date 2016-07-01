package br.com.projetoapp.sharepages.negocio;


import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.LivroDAO;

/**
 *  LivroServices trata solicitações na LivroDAO e ações relacionadas a livro.
 */
public class LivroServices {

    private static LivroServices instancia = new LivroServices();
    private LivroDAO livroDAO;

    private LivroServices(){
        this.livroDAO = LivroDAO.getInstancia();
    }

    public static LivroServices getInstancia() {
        return instancia;
    }

    /**
     * Método que solicita a inserção de um livro no banco de dados.
     * Verifica se o livro a ser inserido já existe, caso já existe
     * ele não é inserido. Caso não exista, ele é inserido
     * @param livro
     * @return um livro
     * @throws SharepagesException
     */
    public Livro inserirLivro(Livro livro) throws SharepagesException {
        Livro livroEncontrado;

        try {
            livroEncontrado = livroDAO.buscarLivro(livro.getNome(), livro.getAutor());
        } catch (Exception e){
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
