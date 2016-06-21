package br.com.projetoapp.sharepages.gui;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.projetoapp.sharepages.R;

public class TelaAnuncio extends Activity {

    private TextView nomeLivro, campoAutorAnuncio, campoEditoraAnuncio, campoPagAnuncio, campoEdicaoAnuncio, campoIdiomaAnuncio,
            campoDispoAnuncio, campoTemaAnuncio, campoCidadeAnuncio, campoDescricaoAnuncio;
    private ImageView previsuFoto;
    private Button botaoAvaliacaoLivro, botaoConversarDono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_anuncio);

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
        previsuFoto = (ImageView) findViewById(R.id.preVisuFoto);
        botaoAvaliacaoLivro = (Button) findViewById(R.id.botaoAvaliacaoLivro);
        botaoConversarDono = (Button) findViewById(R.id.botaoConversarDono);




    }

}
