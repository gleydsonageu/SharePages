package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import java.util.List;
import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.AdapterListLivroPorTema;
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

        listarLivrosPorTema();

    }

    public void listarLivrosPorTema(){
        AdapterListLivroPorTema adapterListLivroPorTema = null;

        int idTema = getIntent().getIntExtra("TemaEscolhido", 0);

        SessaoUsuario.getInstancia().setContext(this);
        List<UnidadeLivro> livroPorTema = unidadeLivroService.buscarLivroPorTema(idTema);
        Log.i("SCRIPT","ID do tema Escolhido ================== "+idTema);
        adapterListLivroPorTema = new AdapterListLivroPorTema(ListaLivrosPorTema.this, livroPorTema);
        listLivrosPorTema.setAdapter(adapterListLivroPorTema);

    }

}