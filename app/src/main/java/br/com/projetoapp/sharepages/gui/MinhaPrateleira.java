package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.AdapterListLivro;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.negocio.SessaoUsuario;
import br.com.projetoapp.sharepages.negocio.UnidadeLivroService;

public class MinhaPrateleira extends Activity {

    private ListView listLivro;
   // private AdapterListLivro adapterListView;

    private UnidadeLivroService unidadeLivroService = UnidadeLivroService.getInstancia(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_prateleira);

        listLivro = (ListView) findViewById(R.id.listaLivros);
     //   listLivro.setOnItemClickListener(this);

        try {
            listaLivrosDeUsario();
            Log.i("SCRIPT","testando a chamada do metodo --"+ listLivro);
        } catch (SharepagesException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        UnidadeLivro unidadeLivro = adapterListView.getItem(position);
//
//        Intent intent = new Intent(this,PerfilDeLivro.class);
//        intent.putExtra("Livro",unidadeLivro.getIdLivro());
//        setResult(Activity.RESULT_OK, intent);
//
//        startActivityForResult(intent,0);
//    }

    public void listaLivrosDeUsario() throws SharepagesException {
        int id = SessaoUsuario.getInstancia().getUsuarioLogado().getId();

        AdapterListLivro adapterListView = null;
        try {
            List<UnidadeLivro> listaLivros = unidadeLivroService.buscarLivroPorUsuario(id);
            Log.i("SCRIPT","buscando livro depois do livro service "+ listaLivros);
            adapterListView = new AdapterListLivro(this, listaLivros);
            Log.i("SCRIPT","buscando livro "+ adapterListView);
            listLivro.setAdapter(adapterListView);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }
}
