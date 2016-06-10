package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Disponibilidade;
import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.infra.ModeloArrayAdapter;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.negocio.DisponibilidadeServices;
import br.com.projetoapp.sharepages.negocio.TemaServices;

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

        //Preencher o spinner com temas
        try{
            adcTemasNoSpinner();
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        try{
            adcDisponibilidadesNoSpinner();
        }catch (Exception e){
            Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void adcDisponibilidadesNoSpinner() throws SharepagesException {
        disponibilidadeSpinnerPerfil = (Spinner) findViewById(R.id.disponibilidadeSpinnerPefil);

        ArrayList<Disponibilidade> disponibilidades = null;
        try {
            disponibilidades = DisponibilidadeServices.getInstancia(this).pegarDisponibilidades();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ModeloArrayAdapter<Disponibilidade> dataAdapter = new ModeloArrayAdapter<Disponibilidade>(this, android.R.layout.simple_spinner_item, disponibilidades);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disponibilidadeSpinnerPerfil.setAdapter(dataAdapter);

        disponibilidadeSpinnerPerfil.getSelectedItem();

    }

    private void adcTemasNoSpinner() throws SharepagesException {
        temaSpinnerPerfil = (Spinner) findViewById(R.id.temaSpinnerPerfil);

        ArrayList<Tema> temas = null;
        try {
            temas = TemaServices.getInstancia(this).pegarCidades();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ModeloArrayAdapter<Tema> dataAdapter = new ModeloArrayAdapter<Tema>(this, android.R.layout.simple_spinner_item, temas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temaSpinnerPerfil.setAdapter(dataAdapter);

        temaSpinnerPerfil.getSelectedItem();

    }

}