package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.infra.AdapterlistTema;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.negocio.TemaServices;

public class ColecaoDisponivel extends Activity {

    private EditText textoPesquisar;
    private Button botaoPesquisar;
    private ListView listaLivros;
    private AdapterlistTema adapterlistTema;

    TemaServices temaServices = TemaServices.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colecao_disponivel);

        textoPesquisar = (EditText) findViewById(R.id.campoPesquisar);
        botaoPesquisar = (Button) findViewById(R.id.btnPesquisar);
        listaLivros = (ListView) findViewById(R.id.listaLivrosColecao);

        try {
            listarTemasDisponiveis();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void listarTemasDisponiveis() throws SharepagesException {

        AdapterlistTema adapterlistTema = null;

        SessaoUsuario.getInstancia().setContext(this);
        ArrayList<Tema> temas = TemaServices.getInstancia().getTemas();
        adapterlistTema = new AdapterlistTema(ColecaoDisponivel.this, temas);
        listaLivros.setAdapter(adapterlistTema);

    }

}
