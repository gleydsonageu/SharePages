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
import br.com.projetoapp.sharepages.infra.DisponibilidadeArrayAdapter;
import br.com.projetoapp.sharepages.infra.TemaArrayAdapter;
import br.com.projetoapp.sharepages.negocio.DisponibilidadeServices;
import br.com.projetoapp.sharepages.negocio.TemaServices;

public class CadastroLivro extends Activity {

    EditText campoNomeLivro, campoAutor, campoEditora, camponDePaginas, campoEdicao, campoDescricao;
    Button selecionarFoto, cadastrarLivro;
    Spinner disponibilidadeSpinner, temaSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        //cadastrarLivro = (Button) findViewById(R.id.cadastrarLivro);


        //Preencher o spinner com temas
        try{
            adcTemasNoSpinner();
        }catch (Exception e) {
            Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
        }



        //Preencher o spinner com disponibilidade
        try {
            adcDisponibilidadesNoSpinner();
        } catch (Exception e) {
            Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
        }




        /*cadastrarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Disponibilidade disponibilidade = (Disponibilidade) disponibilidadeSpinner.getSelectedItem();
                Tema tema = (Tema) temaSpinner.getSelectedItem();
            }
        });*/
    }

    private void adcDisponibilidadesNoSpinner() throws Exception {
        disponibilidadeSpinner = (Spinner) findViewById(R.id.disponibilidadeSpinner);

        ArrayList<Disponibilidade> disponibilidades = DisponibilidadeServices.getInstancia(this).pegarDisponibilidades();

        DisponibilidadeArrayAdapter dataAdapter = new DisponibilidadeArrayAdapter(this, android.R.layout.simple_spinner_item, disponibilidades);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disponibilidadeSpinner.setAdapter(dataAdapter);

        disponibilidadeSpinner.getSelectedItem();

    }

    private void adcTemasNoSpinner() throws Exception {
        temaSpinner = (Spinner) findViewById(R.id.temaSpinner);

        ArrayList<Tema> temas = TemaServices.getInstancia(this).pegarCidades();

        TemaArrayAdapter dataAdapter = new TemaArrayAdapter(this, android.R.layout.simple_spinner_item, temas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temaSpinner.setAdapter(dataAdapter);

        temaSpinner.getSelectedItem();

    }
}
