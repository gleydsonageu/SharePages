package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.projetoapp.sharepages.R;


public class TelaInicial extends Activity implements View.OnClickListener{

    private static final String CATEGORIA = "Script";

    EditText textoUsuario;
    EditText textoSenha;
    Button botaoEntrar;
    TextView botaoFazerCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        Log.i(CATEGORIA, getLocalClassName() + ".onCreate();");

        textoUsuario = (EditText) findViewById(R.id.textoUsuario);
        textoSenha = (EditText) findViewById(R.id.textoSenha);
        botaoEntrar= (Button) findViewById(R.id.botaoEntrar);
        botaoFazerCadastro = (TextView) findViewById(R.id.botaoFazerCadastro);

        botaoEntrar.setOnClickListener(this);
        botaoFazerCadastro.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.botaoEntrar:


                break;

            case R.id.botaoFazerCadastro:

                Intent intent = new Intent(this, CadastroUsuario.class);
                startActivity(intent);

                break;
        }
    }

    //   chamando a tela de cadastro

//    public void ChamarCadastro(View view){
//
//        Intent intent = new Intent(this, CadastroUsuario.class);
//        startActivity(intent);
//
//        Log.i(CATEGORIA, getLocalClassName()+".cadastroChamado");
//    }


}