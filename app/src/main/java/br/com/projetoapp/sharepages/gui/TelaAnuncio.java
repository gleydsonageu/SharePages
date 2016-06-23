package br.com.projetoapp.sharepages.gui;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.negocio.FotoServices;
import br.com.projetoapp.sharepages.negocio.LivroServices;
import br.com.projetoapp.sharepages.negocio.UnidadeLivroService;

public class TelaAnuncio extends Activity {

    private TextView nomeLivro, campoAutorAnuncio, campoEditoraAnuncio, campoPagAnuncio, campoEdicaoAnuncio, campoIdiomaAnuncio,
            campoDispoAnuncio, campoTemaAnuncio, campoCidadeAnuncio, campoDescricaoAnuncio;
    private ImageView preVisuFoto;
    private Button botaoAvaliacaoLivro, botaoConversarDono;

    LivroServices livroServices = LivroServices.getInstancia();
    UnidadeLivroService unidadeLivroService = UnidadeLivroService.getInstancia();
    FotoServices fotoServices = FotoServices.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_anuncio);
        int idUnidadeLivro = getIntent().getIntExtra("UnidadeLivroEscolhido", -1);

        SessaoUsuario.getInstancia().setContext(this);
        UnidadeLivro unidadeLivro = unidadeLivroService.buscarLivroPorId(idUnidadeLivro);


        nomeLivro = (TextView) findViewById(R.id.nomeLivro);
        campoAutorAnuncio = (TextView) findViewById(R.id.campoAutorAnuncio);
        campoEditoraAnuncio = (TextView) findViewById(R.id.campoEditoraAnuncio);
        campoPagAnuncio = (TextView) findViewById(R.id.campoPagAnuncio);
        campoEdicaoAnuncio = (TextView) findViewById(R.id.campoEdicaoAnuncio);
        campoIdiomaAnuncio = (TextView) findViewById(R.id.campoIdiomaAnuncio);
        campoDispoAnuncio = (TextView) findViewById(R.id.campoDispoAnuncio);
        campoTemaAnuncio = (TextView) findViewById(R.id.campoTemaAnuncio);
        campoCidadeAnuncio = (TextView) findViewById(R.id.campoCidadeAnuncio);
        campoDescricaoAnuncio = (TextView) findViewById(R.id.campoDescricaoAnuncio);
        preVisuFoto = (ImageView) findViewById(R.id.preVisuFoto);
        botaoAvaliacaoLivro = (Button) findViewById(R.id.botaoAvaliacaoLivro);
        botaoConversarDono = (Button) findViewById(R.id.botaoConversarDono);

        nomeLivro.setText(unidadeLivro.getLivro().getNome());
        campoAutorAnuncio.setText(unidadeLivro.getLivro().getAutor());
        campoEdicaoAnuncio.setText(unidadeLivro.getEdicao());
        campoEditoraAnuncio.setText(unidadeLivro.getEditora());
        campoPagAnuncio.setText("" + unidadeLivro.getNumeroPaginas());
        campoDescricaoAnuncio.setText(unidadeLivro.getDescricao());
        campoIdiomaAnuncio.setText(unidadeLivro.getIdioma());
        campoTemaAnuncio.setText(unidadeLivro.getLivro().getTema().getNome());
        campoCidadeAnuncio.setText(unidadeLivro.getUsuario().getCidade().getNome());
        campoDispoAnuncio.setText(unidadeLivro.getDisponibilidade().getNome());

        Uri visualizacao = Uri.fromFile(new File(unidadeLivro.getFotos().get(0).getCaminho()));
        preVisuFoto.setImageURI(visualizacao);



    }

}
