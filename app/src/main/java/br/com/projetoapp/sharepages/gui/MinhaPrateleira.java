package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.AdapterListLivro;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.negocio.UnidadeLivroService;

public class MinhaPrateleira extends Activity {

    private ListView listLivro;
    private UnidadeLivroService unidadeLivroService = UnidadeLivroService.getInstancia();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_prateleira);

        listLivro = (ListView) findViewById(R.id.listaLivros);

        try {
            listaLivrosDeUsuarioLogado();
        } catch (SharepagesException e) {
            Toast.makeText(getApplication(), "Erro ao listar livro. ", Toast.LENGTH_LONG).show();
        }
    }

    public void listaLivrosDeUsuarioLogado() throws SharepagesException {
        int id = SessaoUsuario.getInstancia().getUsuarioLogado().getId();

        AdapterListLivro adapterListView = null;

        SessaoUsuario.getInstancia().setContext(this);
        List<UnidadeLivro> listaLivros = unidadeLivroService.buscarLivroPorUsuario(id);
        adapterListView = new AdapterListLivro(MinhaPrateleira.this, listaLivros);
        listLivro.setAdapter(adapterListView);
    }
}