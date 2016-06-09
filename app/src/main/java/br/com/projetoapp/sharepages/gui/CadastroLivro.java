package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Disponibilidade;
import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.ModeloArrayAdapter;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.negocio.DisponibilidadeServices;
import br.com.projetoapp.sharepages.negocio.LivroServices;
import br.com.projetoapp.sharepages.negocio.SessaoUsuario;
import br.com.projetoapp.sharepages.negocio.TemaServices;
import br.com.projetoapp.sharepages.negocio.UnidadeLivroService;

public class CadastroLivro extends Activity {

    private EditText campoNomeLivro, campoAutor, campoEditora, camponDePaginas, campoEdicao, campoDescricao, campoIdioma;
    private Button selecionarFoto, cadastrarLivro;
    private Spinner disponibilidadeSpinner, temaSpinner;

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

        chamarBotaoSelecionarFoto();
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

                    UnidadeLivro unidadeLivro = new UnidadeLivro(editora, nDePaginas, edicao, descricao,idioma,
                            disponibilidade, disponibilidade.getId(),SessaoUsuario.getInstancia().getUsuarioLogado().getId());

                    if (!validarCamposPreenchidosLivro(livro, unidadeLivro)) {
                        Toast.makeText(getApplication(), "Favor preencher todos os campos", Toast.LENGTH_LONG).show();
                        return;
                    }


                    cadastrarLivro(livro, unidadeLivro);

                }catch (NumberFormatException e){
                    Toast.makeText(getApplication(),"insira numeros de paginas",Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(CadastroLivro.this, MenuPrincipal.class);
                startActivity(intent);

            }

        });
    }

    //validação de campos preenchidos
    public boolean validarCamposPreenchidosLivro(Livro livro, UnidadeLivro unidadeLivro) {
        boolean validacao = true;
        Log.i("SCRIPT", "Chamada do metodo validar campos vazios ");
        if (livro.getNome() == null) {
            validacao = false;
            campoNomeLivro.setError(getString(R.string.campo_obrigatorio));
        }
        if (livro.getAutor() == null) {
            validacao = false;
            campoAutor.setError(getString(R.string.campo_obrigatorio));
        }
        if (unidadeLivro.getEdicao() == null) {
            campoEdicao.setError(getString(R.string.campo_obrigatorio));
        }
        if (unidadeLivro.getEditora() == null) {
            campoEditora.setError(getString(R.string.campo_obrigatorio));
        }
        return validacao;
    }



    private void adcDisponibilidadesNoSpinner() throws Exception {
        disponibilidadeSpinner = (Spinner) findViewById(R.id.disponibilidadeSpinner);

        ArrayList<Disponibilidade> disponibilidades = DisponibilidadeServices.getInstancia(this).pegarDisponibilidades();

        ModeloArrayAdapter<Disponibilidade> dataAdapter = new ModeloArrayAdapter<Disponibilidade>(this, android.R.layout.simple_spinner_item, disponibilidades);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disponibilidadeSpinner.setAdapter(dataAdapter);

        disponibilidadeSpinner.getSelectedItem();

    }

    private void adcTemasNoSpinner() throws Exception {
        temaSpinner = (Spinner) findViewById(R.id.temaSpinner);

        ArrayList<Tema> temas = TemaServices.getInstancia(this).pegarCidades();

        ModeloArrayAdapter<Tema> dataAdapter = new ModeloArrayAdapter<Tema>(this, android.R.layout.simple_spinner_item, temas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temaSpinner.setAdapter(dataAdapter);

        temaSpinner.getSelectedItem();

    }

    public void cadastrarLivro(Livro livro, UnidadeLivro unidadeLivro){

        try {
            livro = livroServices.inserirLivro(livro);
            unidadeLivro.setIdLivro(livro.getId());
            unidadeLivroService.inserirUnidadeLivro(unidadeLivro);
            Toast.makeText(getApplication(),"Livro cadastrado",Toast.LENGTH_LONG).show();
            finish();
        } catch (SharepagesException e) {

        }
    }

    public void chamarBotaoSelecionarFoto() {

        selecionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeFoto = DateFormat.format(
                        "yyyy-MM-dd_hhmmss", new Date()).toString();

               File caminhoFoto = new File(
                        Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES),
                        nomeFoto);

                Intent it = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                it.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(caminhoFoto));
                startActivityForResult(it, 0);
            }

           });

    }
}
