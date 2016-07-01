package br.com.projetoapp.sharepages.negocio;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
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
         int  idUnidadeLivro = (int) unidadeLivroDAO.inserirUnidadeLivro(unidadeLivro);
            unidadeLivro.setId(idUnidadeLivro);
        } catch (SharepagesException e) {
            throw new SharepagesException("Houve um erro inserirUsuario livro");
        }
        return unidadeLivro;
    }

    public List<UnidadeLivro> buscarLivroPorUsuario(int id){

        return unidadeLivroDAO.buscarLivroPorIdUsuario(id);
    }

    public UnidadeLivro buscarLivroPorId(int id) {
        return unidadeLivroDAO.buscarLivroPorId(id);
    }

    public List<UnidadeLivro> buscarLivroPorTema(int id){
        int idUsuarioLogado = SessaoUsuario.getInstancia().getUsuarioLogado().getId();
        return unidadeLivroDAO.buscarLivroPorTema(id, idUsuarioLogado);
    }

    public List<UnidadeLivro> buscarLivroPorNomeOuAutor(String nome){
        int idUsuarioLogado = SessaoUsuario.getInstancia().getUsuarioLogado().getId();
        return unidadeLivroDAO.buscarLivroPorNomeOuAutor(nome,idUsuarioLogado);
    }

    public void alterarUnidadeLivro(UnidadeLivro alteracaoUnidadeLivro) throws SharepagesException{
        unidadeLivroDAO.alterarUnidadeLivro(alteracaoUnidadeLivro);
    }

    public void alterarSituacao(UnidadeLivro alteracaoSituacao) throws SharepagesException{
        unidadeLivroDAO.alterarSituacao(alteracaoSituacao);
    }

    public String buscarAvaliacaoLivro(UnidadeLivro unidadeLivro) throws IOException {
        String nomeLivroAvaliacao = unidadeLivro.getLivro().getNome();
        String autorAvaliacao = unidadeLivro.getLivro().getAutor();
        String editoraAvaliacao = unidadeLivro.getEditora();
        String query = nomeLivroAvaliacao +" "+autorAvaliacao+" "+editoraAvaliacao;
        String resultadoAvaliacao = null;

        Document doc = Jsoup.connect("https://www.amazon.com.br/s/")
                .userAgent("Mozilla")
                .data("field-keywords", query)
                .get();
        Elements resultadosEncontrados = doc.select(".s-result-item");

        if (resultadosEncontrados.size() > 0) {
            Element primeiroResultado = resultadosEncontrados.first();
            Elements elementosAvaliacao = primeiroResultado.select(".a-icon-alt");

            if (elementosAvaliacao.size() > 0) {
                Element elementoAvaliacao = elementosAvaliacao.first();
                resultadoAvaliacao = elementoAvaliacao.toString();
            }
        }
        return resultadoAvaliacao;
    }
}
