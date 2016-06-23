package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.List;
import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.AdapterListLivroDisponivel;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.negocio.UnidadeLivroService;

public class ListaLivrosPorTema extends Activity {

    private ListView listLivrosPorTema;
    private UnidadeLivroService unidadeLivroService = UnidadeLivroService.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_livros_por_tema);

        listLivrosPorTema = (ListView) findViewById(R.id.listaLivrosPorTema);
        listLivrosPorTema.setOnItemClickListener(chamarTelaAnuncio());

        listarLivrosPorTema();

    }

    public void listarLivrosPorTema() {
        AdapterListLivroDisponivel adapterListLivroDisponivel = null;

        int idTema = getIntent().getIntExtra("TemaEscolhido", 0);

        SessaoUsuario.getInstancia().setContext(this);
        List<UnidadeLivro> livroPorTema = unidadeLivroService.buscarLivroPorTema(idTema);
        Log.i("SCRIPT", "ID do tema Escolhido ================== " + idTema);
        adapterListLivroDisponivel = new AdapterListLivroDisponivel(ListaLivrosPorTema.this, livroPorTema);
        listLivrosPorTema.setAdapter(adapterListLivroDisponivel);

    }

    public AdapterView.OnItemClickListener chamarTelaAnuncio() {
        return (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UnidadeLivro unidadeLivro = (UnidadeLivro) listLivrosPorTema.getAdapter().getItem(position);

                Intent intent = new Intent(view.getContext(), TelaAnuncio.class);
                intent.putExtra("UnidadeLivroEscolhido", unidadeLivro.getId());
                view.getContext().startActivity(intent);
            }
        });

    }
}