package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;

public class PerfilDeLivro extends Activity {

    private EditText campoNomeLivroPerfil, campoAutorPerfil, campoEditoraPerfil, campoDePaginasPerfil, campoEdicaoPerfil, campoDescricaoPerfil, campoIdiomaPerfil;
    private Button atualizarFoto, atualizarLivro;
    private Spinner disponibilidadeSpinnerPerfil, temaSpinnerPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_de_livro);

        campoNomeLivroPerfil = (EditText) findViewById(R.id.campoNomeLivroPerfil);
        campoAutorPerfil = (EditText) findViewById(R.id.campoAutorPerfil);
        campoEditoraPerfil = (EditText) findViewById(R.id.campoEditoraPerfil);
        campoDePaginasPerfil = (EditText) findViewById(R.id.campoNdePaginasPerfil);
        campoDescricaoPerfil = (EditText) findViewById(R.id.campoDescricaoPerfil);
        campoIdiomaPerfil = (EditText) findViewById(R.id.campoDescricaoPerfil);
        atualizarLivro = (Button) findViewById(R.id.atualizarLivro);
        atualizarFoto = (Button) findViewById(R.id.atualizarFoto);
        disponibilidadeSpinnerPerfil = (Spinner) findViewById(R.id.disponibilidadeSpinnerPerfil);
        temaSpinnerPerfil = (Spinner) findViewById(R.id.temaSpinnerPerfil);


    }

    public void carregarPerfilDeLivro(){
        Usuario usuario = SessaoUsuario.getInstancia().getUsuarioLogado();


    }

}