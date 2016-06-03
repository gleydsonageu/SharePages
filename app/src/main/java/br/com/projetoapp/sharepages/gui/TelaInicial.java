package br.com.projetoapp.sharepages.gui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.negocio.UsuarioServices;

public class TelaInicial extends Activity {

    private EditText textoUsuario;
    private EditText textoSenha;
    private Button botaoEntrar;
    private TextView botaoFazerCadastro;

    UsuarioServices usuarioServices = UsuarioServices.getInstancia(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

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
                    loginUsuario();
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

    public void loginUsuario(){
        Usuario usuario = new Usuario();
        usuario.setEmail(textoUsuario.getText().toString());
        usuario.setSenha(textoSenha.getText().toString());

        try {
            usuarioServices.validarLoginUsuario(usuario);
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

}