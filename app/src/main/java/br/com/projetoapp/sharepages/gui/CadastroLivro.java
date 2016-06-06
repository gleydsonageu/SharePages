package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Disponibilidade;
import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.DisponibilidadeArrayAdapter;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.infra.TemaArrayAdapter;
import br.com.projetoapp.sharepages.negocio.DisponibilidadeServices;
import br.com.projetoapp.sharepages.negocio.LivroServices;
import br.com.projetoapp.sharepages.negocio.SessaoUsuario;
import br.com.projetoapp.sharepages.negocio.TemaServices;
import br.com.projetoapp.sharepages.negocio.UnidadeLivroService;

public class CadastroLivro extends Activity {

    EditText campoNomeLivro, campoAutor, campoEditora, camponDePaginas, campoEdicao, campoDescricao, campoIdioma;
    Button selecionarFoto, cadastrarLivro;
    Spinner disponibilidadeSpinner, temaSpinner;

    LivroServices livroServices = LivroServices.getInstancia(this);
    UnidadeLivroService unidadeLivroService = UnidadeLivroService.getInstancia(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        cadastrarLivro = (Button) findViewById(R.id.cadastrarLivroMenu);
        selecionarFoto = (Button) findViewById(R.id.selecionarFoto);
        campoNomeLivro = (EditText) findViewById(R.id.campoNomeLivro);
        campoAutor = (EditText) findViewById(R.id.campoAutor);
        campoEditora = (EditText) findViewById(R.id.campoEditora);
        camponDePaginas = (EditText) findViewById(R.id.campoNdePaginas);
        campoEdicao = (EditText) findViewById(R.id.campoEdicao);
        campoDescricao = (EditText) findViewById(R.id.campoDescricao);
        campoIdioma = (EditText) findViewById(R.id.campoIdioma);


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


        chamarBotaoCadastrarLivro();


    }

    public void chamarBotaoCadastrarLivro() {

        cadastrarLivro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nome = campoNomeLivro.getText().toString();
                String autor = campoAutor.getText().toString();
                String editora = campoEditora.getText().toString();

                try {
                    int nDePaginas = Integer.parseInt(camponDePaginas.getText().toString());
                    String edicao = campoEdicao.getText().toString();
                    String descricao = campoDescricao.getText().toString();
                    String idioma = campoIdioma.getText().toString();
                    Disponibilidade disponibilidade = (Disponibilidade) disponibilidadeSpinner.getSelectedItem();
                    Tema tema = (Tema) temaSpinner.getSelectedItem();

                    Livro livro = new Livro(nome, autor, tema, tema.getId());

                    UnidadeLivro unidadeLivro = new UnidadeLivro(editora, nDePaginas, edicao, descricao,idioma, disponibilidade, disponibilidade.getId(),SessaoUsuario.getInstancia().getUsuarioLogado().getId());

                    cadastrarLivro(livro, unidadeLivro);

                }catch (NumberFormatException e){
                    Toast.makeText(getApplication(),"insira numeros de paginas",Toast.LENGTH_LONG).show();
                }

            }

        });
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

    public void cadastrarLivro(Livro livro, UnidadeLivro unidadeLivro){

        try {
            livro = livroServices.inserirLivroSeNaoExistir(livro);
            unidadeLivro.setIdLivro(livro.getId());
            unidadeLivroService.inserirUnidadeLivro(unidadeLivro);
            Toast.makeText(getApplication(),"Livro cadastrado",Toast.LENGTH_LONG).show();
            finish();
        } catch (SharepagesException e) {

        }
    }
}
