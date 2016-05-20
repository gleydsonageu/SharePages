package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.negocio.SessaoUsuario;
import br.com.projetoapp.sharepages.negocio.UsuarioServices;
import br.com.projetoapp.sharepages.persistencia.UsuarioDAO;

public class TelaInicial extends Activity {


    private EditText textoUsuario;
    private EditText textoSenha;
    private Button botaoEntrar;
    private TextView botaoFazerCadastro;
    private static Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_tela_inicial);

        textoUsuario = (EditText) findViewById(R.id.textoUsuario);
        textoSenha = (EditText) findViewById(R.id.textoSenha);
        botaoEntrar = (Button) findViewById(R.id.botaoEntrar);
        botaoFazerCadastro = (TextView) findViewById(R.id.botaoFazerCadastro);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textoUsuario.getText().length() == 0 || textoSenha.getText().length() == 0) {
                    Toast.makeText(getApplication(), "Por favor preencha o usuario/senha", Toast.LENGTH_LONG).show();
                } else {
                    Usuario usuario = new Usuario();
                    usuario.setEmail(textoUsuario.getText().toString());
                    usuario.setSenha(textoSenha.getText().toString());

                    try {
                        Usuario usuarioEncontrado = UsuarioServices.getInstancia().validarCadastroUsuario(usuario);
                        SessaoUsuario.getInstancia().setUsuarioLogado(usuarioEncontrado);
                        Toast.makeText(getApplication(), "Tudo ok! Seja bem vindo!", Toast.LENGTH_LONG).show();

                        Intent intentAbrirMenuPrincipal = new Intent(TelaInicial.this, MenuPrincipal.class);
                        startActivity(intentAbrirMenuPrincipal);
                    } catch (Exception e) {
                        Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public static Context getContext() {
        return context;
    }
}