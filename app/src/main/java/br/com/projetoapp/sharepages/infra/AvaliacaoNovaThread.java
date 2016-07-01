package br.com.projetoapp.sharepages.infra;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;


import br.com.projetoapp.sharepages.dominio.UnidadeLivro;

public class AvaliacaoNovaThread extends AsyncTask<UnidadeLivro, Void, String> {

    private Exception exception;
    private TextView avaliacaoLivroTextView;

    public AvaliacaoNovaThread(TextView avaliacaoLivroTextView) {
        this.avaliacaoLivroTextView = avaliacaoLivroTextView;
    }

    protected String doInBackground(UnidadeLivro... params) {
        String resultadoAvaliacao = null;
        try {
            UnidadeLivro unidadeLivro = params[0];
            String nomeLivroAvaliacao = unidadeLivro.getLivro().getNome();
            String autorAvaliacao = unidadeLivro.getLivro().getAutor();
            String editoraAvaliacao = unidadeLivro.getEditora();
            String query = nomeLivroAvaliacao +" "+autorAvaliacao+" "+editoraAvaliacao;

            Document doc = Jsoup.connect("https://www.amazon.com.br/s/")
                    .userAgent("Mozilla")
                    .data("field-keywords", query)
                    .get();
            doc.body();

            Elements resultadosEncontrados = doc.select(".s-item-container");

            if (resultadosEncontrados.size() > 0) {
                Element primeiroResultado = resultadosEncontrados.first();
                Elements elementosAvaliacao = primeiroResultado.select(".a-icon-alt");

                if (elementosAvaliacao.size() > 0) {
                    Element elementoAvaliacao = elementosAvaliacao.first();
                    resultadoAvaliacao = elementoAvaliacao.text();
                } else {
                    resultadoAvaliacao = "Livro não possui avaliação";
                }
            } else {
                resultadoAvaliacao = "Livro não foi avaliado";
            }


        }catch (IOException e) {
            resultadoAvaliacao = null;
        }

        return resultadoAvaliacao;
    }

    protected void onPostExecute(String avaliacao) {
        if(avaliacao == null) {
            Toast.makeText(avaliacaoLivroTextView.getContext(), "Erro ao buscar avaliação", Toast.LENGTH_LONG).show();
        } else {
            avaliacaoLivroTextView.setText(avaliacao);
        }
    }

}
