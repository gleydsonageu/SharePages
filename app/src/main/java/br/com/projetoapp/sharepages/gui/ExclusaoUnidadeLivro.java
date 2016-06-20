package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.EncerramentoDoAnuncio;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.negocio.UnidadeLivroService;

public class ExclusaoUnidadeLivro extends Activity {
    private Button botaoTransacao, botaoDesistencia;

    UnidadeLivroService unidadeLivroService = UnidadeLivroService.getInstancia();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exclusao_livro);
        int idUnidadeLivro = getIntent().getIntExtra("UnidadeLivro", -1);

        UnidadeLivro unidadeLivro = new UnidadeLivro();
        unidadeLivro.setId(idUnidadeLivro);

        botaoTransacao = (Button) findViewById(R.id.botaoTransacao);
        botaoDesistencia = (Button) findViewById(R.id.botaoDesistencia);


        chamarBotaoTransacao(unidadeLivro);
        chamarBotaoDesistencia(unidadeLivro);


    }

    public void chamarBotaoTransacao(final UnidadeLivro unidadeLivro) {

        botaoTransacao.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                unidadeLivro.setSituacao(EncerramentoDoAnuncio.TRANSACAOEFETUADA);
                try {
                    SessaoUsuario.getInstancia().setContext(ExclusaoUnidadeLivro.this);
                    unidadeLivroService.alterarSituacao(unidadeLivro);
                    Toast.makeText(getApplication(), "Livro Excluido!", Toast.LENGTH_LONG).show();
                    finish();
                } catch (SharepagesException e) {
                    Toast.makeText(getApplication(), "Por problemas internos, o livro não foi excluido!", Toast.LENGTH_LONG).show();

                }


            }
        });
    }
    public void chamarBotaoDesistencia(final UnidadeLivro unidadeLivro) {
        botaoDesistencia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                unidadeLivro.setSituacao(EncerramentoDoAnuncio.DESISTENCIA);
                try {
                    SessaoUsuario.getInstancia().setContext(ExclusaoUnidadeLivro.this);
                    unidadeLivroService.alterarSituacao(unidadeLivro);
                    Toast.makeText(getApplication(), "Livro Excluido!", Toast.LENGTH_LONG).show();
                    finish();
                } catch (SharepagesException e) {
                    Toast.makeText(getApplication(), "Por problemas internos, o livro não foi excluido!", Toast.LENGTH_LONG).show();

                }


            }
        });
    }

}