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
import br.com.projetoapp.sharepages.infra.AdapterListLivro;
import br.com.projetoapp.sharepages.negocio.LivroServices;

public class MinhaPrateleira extends Activity implements AdapterView.OnItemClickListener {

    private ListView listLivro;
    private AdapterListLivro adapterListView;
    private LivroServices livroServices;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_prateleira);

        listLivro = (ListView) findViewById(R.id.listaLivros);
        listLivro.setOnItemClickListener(this);
        livroServices = LivroServices.getInstancia(this);

        listaLivro();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Livro livro = adapterListView.getItem(position);

        Intent intent = new Intent(this,PerfilDeLivro.class);
        intent.putExtra("Livro",livro.getId());
        setResult(Activity.RESULT_OK, intent);

        startActivityForResult(intent,0);
    }


    public void listaLivro(){
        String nome = null;
        adapterListView = null;

        List<Livro> listaLivros = livroServices.consultarLivro(nome);
        adapterListView = new AdapterListLivro(this, listaLivros);
        Log.i("SCRIPT","buscando livro"+ listaLivros);
        listLivro.setAdapter(adapterListView);
    }

}
