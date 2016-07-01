package br.com.projetoapp.sharepages.negocio;


import java.util.List;

import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.UnidadeLivroDAO;

/**
 * UnidadeLivroServices trata solicitações na UnidadeLivroDAO e ações relacionadas a unidadeLivro.
 */

public class UnidadeLivroServices {

    private static UnidadeLivroServices instancia = new UnidadeLivroServices();
    private UnidadeLivroDAO unidadeLivroDAO;

    private UnidadeLivroServices(){
        this.unidadeLivroDAO = UnidadeLivroDAO.getInstacia();
    }

    public static UnidadeLivroServices getInstancia(){
        return instancia;
    }

    /**
     * Método que solicita a  inserção de  uma unidadeLivro na tabela de unidadeLivro no banco de dados
     * @param unidadeLivro
     * @return unidadelivro
     * @throws SharepagesException
     */
    public UnidadeLivro inserirUnidadeLivro(UnidadeLivro unidadeLivro) throws SharepagesException{

        try {
         int  idUnidadeLivro = (int) unidadeLivroDAO.inserirUnidadeLivro(unidadeLivro);
            unidadeLivro.setId(idUnidadeLivro);
        } catch (SharepagesException e) {
            throw new SharepagesException("Houve um erro inserirUsuario livro");
        }
        return unidadeLivro;
    }

    /**
     * Método que solicita a busca de todos os livros do usuario e guarda em uma lista
     * @param id
     * @return uma lista com todos os livros cadastrados do usuario
     */
    public List<UnidadeLivro> buscarLivroPorUsuario(int id){

        return unidadeLivroDAO.buscarLivroPorIdUsuario(id);
    }

    /**
     * Método que solicita a busca de uma unidadeLivro do banco pelo seu id
     * @param id
     * @return uma unidadeLivro com o id especificado
     */
    public UnidadeLivro buscarLivroPorId(int id) {
        return unidadeLivroDAO.buscarLivroPorId(id);
    }

    /**
     * Método que solicita a  busca de todos os livros cadastrados com um id de tema espefico
     * @param id
     * @return uma lista com todos os livros cadastrados por um tema espefico
     */
    public List<UnidadeLivro> buscarLivroPorTema(int id){
        int idUsuarioLogado = SessaoUsuario.getInstancia().getUsuarioLogado().getId();
        return unidadeLivroDAO.buscarLivroPorTema(id, idUsuarioLogado);
    }

    /**
     * Método que solicita uma consulta por nome do autor ou nome do livro
     * @param nome
     * @return uma lista com os livros encontrados
     */
    public List<UnidadeLivro> buscarLivroPorNomeOuAutor(String nome){
        int idUsuarioLogado = SessaoUsuario.getInstancia().getUsuarioLogado().getId();
        return unidadeLivroDAO.buscarLivroPorNomeOuAutor(nome,idUsuarioLogado);
    }

    /**
     * Método que solicita a alteração de uma unidadeLivro no banco de dados
     * @param alteracaoUnidadeLivro
     * @throws SharepagesException
     */
    public void alterarUnidadeLivro(UnidadeLivro alteracaoUnidadeLivro) throws SharepagesException{
        unidadeLivroDAO.alterarUnidadeLivro(alteracaoUnidadeLivro);
    }

    /**
     * Método que solicita a alteração da situação do livro no banco de dados
     * @param alteracaoSituacao
     * @throws SharepagesException
     */
    public void alterarSituacao(UnidadeLivro alteracaoSituacao) throws SharepagesException{
        unidadeLivroDAO.alterarSituacao(alteracaoSituacao);
    }
}
