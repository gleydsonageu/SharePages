package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.AdapterListLivroDisponivel;
import br.com.projetoapp.sharepages.infra.AdapterListTema;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.negocio.TemaServices;
import br.com.projetoapp.sharepages.negocio.UnidadeLivroService;

public class ColecaoDisponivel extends Activity {

    private EditText textoPesquisar;
    private ListView listaTemas;
    private ListView listaLivrosDisponiveis;
    private AdapterListTema adapterListTema;
    private AdapterListLivroDisponivel adapterListLivroDisponivel;
    private UnidadeLivroService unidadeLivroService = UnidadeLivroService.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colecao_disponivel);

        textoPesquisar = (EditText) findViewById(R.id.campoPesquisar);
        listaLivrosDisponiveis = (ListView) findViewById(R.id.listaLivrosDisponiveis);
        listaLivrosDisponiveis.setOnItemClickListener(chamarTelaAnuncio());
        listaTemas = (ListView) findViewById(R.id.listaTemas);
        listaTemas.setOnItemClickListener(chamarListaDeLivrosPorTemas());

        textoPesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            pequisaLivroDisponivel();
            }
        });

        try {
            listarTemasDisponiveis();
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Erro ao listar temas", Toast.LENGTH_LONG).show();
        }
    }

    public void listarTemasDisponiveis() throws SharepagesException {

        AdapterListTema adapterListTema = null;

        SessaoUsuario.getInstancia().setContext(this);
        List<Tema> temas = TemaServices.getInstancia().getTemas();
        adapterListTema = new AdapterListTema(ColecaoDisponivel.this, temas);
        listaTemas.setAdapter(adapterListTema);
    }

    public AdapterView.OnItemClickListener chamarListaDeLivrosPorTemas(){
        return (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Tema tema = (Tema) listaTemas.getAdapter().getItem(position);

                Intent intent = new Intent( view.getContext(), ListaLivrosPorTema.class);
                intent.putExtra("TemaEscolhido",tema.getId());

                view.getContext().startActivity(intent);
            }
        });

    }

    public AdapterView.OnItemClickListener chamarTelaAnuncio() {
        return (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UnidadeLivro unidadeLivro = (UnidadeLivro) listaLivrosDisponiveis.getAdapter().getItem(position);

                Intent intent = new Intent(view.getContext(), TelaAnuncio.class);
                intent.putExtra("UnidadeLivroEscolhido", unidadeLivro.getId());
                view.getContext().startActivity(intent);
            }
        });

    }

    public void pequisaLivroDisponivel(){
        String nome = textoPesquisar.getText().toString();
        adapterListLivroDisponivel = null;
        if(nome.length() > 0){

            List<UnidadeLivro> livros = new ArrayList<UnidadeLivro>();
            livros.addAll(unidadeLivroService.buscarLivroPorNomeOuAutor(nome));
            adapterListLivroDisponivel = new AdapterListLivroDisponivel(this, livros);
        }
        listaLivrosDisponiveis.setAdapter(adapterListLivroDisponivel);
    }

}