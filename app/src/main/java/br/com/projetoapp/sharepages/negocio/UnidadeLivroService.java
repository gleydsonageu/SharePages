package br.com.projetoapp.sharepages.negocio;


import java.util.List;

import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.UnidadeLivroDAO;

public class UnidadeLivroService {


    private static UnidadeLivroService instancia = new UnidadeLivroService();
    private UnidadeLivroDAO unidadeLivroDAO;

    private UnidadeLivroService(){
        this.unidadeLivroDAO = UnidadeLivroDAO.getInstacia();
    }

    public static UnidadeLivroService getInstancia(){
        return instancia;
    }

    public UnidadeLivro inserirUnidadeLivro(UnidadeLivro unidadeLivro) throws SharepagesException{

        try {
         int  idUnidadeLivro = (int) unidadeLivroDAO.setUnidadeLivro(unidadeLivro);
            unidadeLivro.setId(idUnidadeLivro);
        } catch (SharepagesException e) {
            throw new SharepagesException("Houve um erro inserir livro");
        }
        return unidadeLivro;
    }
    public List<UnidadeLivro> buscarLivroPorUsuario(int id){

        return unidadeLivroDAO.getLivroPorIdUsuario(id);
    }

    public UnidadeLivro buscarUnidadeLivroPorId(int id) {
        return unidadeLivroDAO.getPorId(id);
    }

    public void alterarLivro(UnidadeLivro alteracaoUnidadeLivro) throws SharepagesException{
        unidadeLivroDAO.alterar(alteracaoUnidadeLivro);
    }

    public void alterarSituacao(UnidadeLivro alteracaoSituacao) throws SharepagesException{
        unidadeLivroDAO.alterarSituacao(alteracaoSituacao);
    }
}
