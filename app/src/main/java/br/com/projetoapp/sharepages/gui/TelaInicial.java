package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.negocio.SessaoUsuario;
import br.com.projetoapp.sharepages.negocio.UsuarioServices;


public class TelaInicial extends Activity {


    private EditText textoUsuario;
    private EditText textoSenha;
    private Button botaoEntrar;
    private TextView botaoFazerCadastro;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        context = this;
        textoUsuario = (EditText) findViewById(R.id.textoUsuario);
        textoSenha = (EditText) findViewById(R.id.textoSenha);
        botaoEntrar = (Button) findViewById(R.id.botaoEntrar);
        botaoFazerCadastro = (TextView) findViewById(R.id.botaoFazerCadastro);




        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validarCampos()) {
                    Toast.makeText(getApplication(), "Por favor preencha o usuario/senha", Toast.LENGTH_LONG).show();
                } else {
                    logarUsuario();
                }
            }
        });

        botaoFazerCadastro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaInicial.this, CadastroUsuario.class);
                startActivity(intent);
            }
        });
    }

    public boolean validarCampos(){
        return textoUsuario.getText().length() == 0 || textoSenha.getText().length() == 0;
    }

    public void logarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setEmail(textoUsuario.getText().toString());
        usuario.setSenha(textoSenha.getText().toString());
        try {
            Usuario usuarioEncontrado = UsuarioServices.getInstancia().validarUsuario(usuario);
            SessaoUsuario.getInstancia().setUsuarioLogado(usuarioEncontrado);
            Toast.makeText(getApplication(), "Seja bem vindo!", Toast.LENGTH_LONG).show();

            chamarMenuPrincipal();

        } catch (Exception e) {
            Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void chamarMenuPrincipal(){
        Intent intentAbrirMenuPrincipal = new Intent(TelaInicial.this, MenuPrincipal.class);
        startActivity(intentAbrirMenuPrincipal);
    }
    //Lembrar de alterar a a chamada do context
    public static Context getContext() {
        return context;
    }

}