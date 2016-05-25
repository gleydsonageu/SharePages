package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.negocio.UsuarioServices;

public class CadastroUsuario extends Activity implements View.OnClickListener {

    EditText textoNome, textoEmail, textoSenha;
    Button botaoCadastrar;

    private UsuarioServices usuarioServices = UsuarioServices.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        textoNome = (EditText) findViewById(R.id.textoNome);
        textoEmail = (EditText) findViewById(R.id.textoEmail);
        textoSenha = (EditText) findViewById(R.id.textoSenha);
        botaoCadastrar = (Button) findViewById(R.id.botaoCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String nome = textoNome.getText().toString().trim();
                String email = textoEmail.getText().toString().trim();
                String senha = textoSenha.getText().toString().trim();

                Usuario usuario = new Usuario(nome, email, senha);
                try {
                    cadastrar(usuario);
                    Log.i("SCRIPT", "Chamando metodo para cadastrar " + nome);
                    Log.i("SCRIPT", "Chamando metodo para cadastrar " + email);
                    Log.i("SCRIPT", "Chamando metodo para cadastrar " + senha);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplication(),"usuario ao cadastrar",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    public void cadastrar(Usuario usuario) throws Exception {
        try {

            usuarioServices.inserirUsuario(usuario);
            Log.i("SCRIPT", "Chamando metodo " + usuario);
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplication(),"falha no cadastro",Toast.LENGTH_LONG).show();
        }
            finish();
    }
}