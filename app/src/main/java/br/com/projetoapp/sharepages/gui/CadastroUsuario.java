package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.projetoapp.sharepages.R;

public class CadastroUsuario extends Activity implements View.OnClickListener {

    EditText textoNome, textoEmail, textoSenha;
    Button botaoCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        textoNome = (EditText) findViewById(R.id.textoNome);
        textoEmail = (EditText) findViewById(R.id.textoEmail);
        textoSenha = (EditText) findViewById(R.id.textoSenha);

        botaoCadastrar = (Button) findViewById(R.id.botaoCadastrar);
        botaoCadastrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
