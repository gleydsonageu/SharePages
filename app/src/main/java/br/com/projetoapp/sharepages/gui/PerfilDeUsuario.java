package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Cidade;
import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.negocio.SessaoUsuario;

public class PerfilDeUsuario extends Activity {

    EditText textoNomePerfil, textoEmailPerfil, textoSenhaAtual, textoNovaSenha;
    Button botaoAtualizar, botaoDeletar;
    Spinner cidadeSpinnerPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_de_usuario);

        textoNomePerfil = (EditText) findViewById(R.id.textoNomePerfil);
        textoEmailPerfil = (EditText) findViewById(R.id.textoEmailPerfil);
        textoSenhaAtual = (EditText) findViewById(R.id.textoSenhaAtual);
        textoNovaSenha = (EditText) findViewById(R.id.textoNovaSenha);
        cidadeSpinnerPerfil = (Spinner) findViewById(R.id.cidadeSpinnerPerfil);
        botaoAtualizar = (Button) findViewById(R.id.botaoAtualizar);
        botaoDeletar = (Button) findViewById(R.id.botaoDeletar);

        Cidade cidade = (Cidade) cidadeSpinnerPerfil.getSelectedItem();

        Intent intent = getIntent();
        if (intent != null) {
                Usuario usuario = SessaoUsuario.getInstancia().getUsuarioLogado();

                Log.i("SCRIPT", "usaurio logado" + usuario);

                textoNomePerfil.setText(usuario.getNome());
                textoEmailPerfil.setText(usuario.getEmail());
                textoNovaSenha.setText(usuario.getSenha());

                //Log.i("SCRIPT", "usaurio logado-----------------" + usuario);

        }

    }

}