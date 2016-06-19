package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.negocio.FotoServices;
import br.com.projetoapp.sharepages.negocio.LivroServices;
import br.com.projetoapp.sharepages.negocio.UnidadeLivroService;

public class Anuncio extends Activity {
   /* private TextView nomeLivro, autorAnuncio, editoraAnuncio, nDePaginasAnuncio, edicaoAnuncio,
            idiomaAnuncio, disponibilidadeAnuncio, temaAnuncio, descricaoAnuncio, cidadeUsuario;
    private ImageView preVisuFoto;
    private Button avaliacaoAnuncio, conversarDono;



    LivroServices livroServices = LivroServices.getInstancia();
    UnidadeLivroService unidadeLivroService = UnidadeLivroService.getInstancia();
    FotoServices fotoServices = FotoServices.getInstancia();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);
        int idUnidadeLivro = getIntent().getIntExtra("UnidadeLivro", -1);

        UnidadeLivro unidadeLivro = unidadeLivroService.buscarUnidadeLivroPorId(idUnidadeLivro);

        nomeLivro = (TextView) findViewById(R.id.nomeLivro);
        autorAnuncio = (TextView) findViewById(R.id.autorAnuncio);
        editoraAnuncio = (TextView) findViewById(R.id.editoraAnuncio);
        nDePaginasAnuncio = (TextView) findViewById(R.id.nDePaginasAnuncio);
        edicaoAnuncio = (TextView) findViewById(R.id.edicaoAnuncio);
        idiomaAnuncio = (TextView) findViewById(R.id.idiomaAnuncio);
        disponibilidadeAnuncio = (TextView) findViewById(R.id.disponibilidadeAnuncio);
        temaAnuncio = (TextView) findViewById(R.id.temaAnuncio);
        descricaoAnuncio = (TextView) findViewById(R.id.descricao);
        cidadeUsuario = (TextView) findViewById(R.id.cidadeUsuarioAnuncio);
        preVisuFoto = (ImageView) findViewById(R.id.preVisuFoto);
        avaliacaoAnuncio = (Button) findViewById(R.id.avaliacaoAnuncio);
        conversarDono = (Button) findViewById(R.id.conversarDono);


        nomeLivro.setText(unidadeLivro.getLivro().getNome());
        autorAnuncio.setText(unidadeLivro.getLivro().getAutor());
        edicaoAnuncio.setText(unidadeLivro.getEdicao());
        editoraAnuncio.setText(unidadeLivro.getEditora());
        nDePaginasAnuncio.setText("" + unidadeLivro.getNumeroPaginas());
        descricaoAnuncio.setText(unidadeLivro.getDescricao());
        idiomaAnuncio.setText(unidadeLivro.getIdioma());
        temaAnuncio.setText(unidadeLivro.getLivro().getTema().getNome());
        disponibilidadeAnuncio.setText(unidadeLivro.getDisponibilidade().getNome());
        Uri visualizacao = Uri.fromFile(new File(unidadeLivro.getFotos().get(0).getCaminho()));
        preVisuFoto.setImageURI(visualizacao);
       // cidadeUsuario.setText(unidadeLivro.getIdUsuario());








    }*/
}
