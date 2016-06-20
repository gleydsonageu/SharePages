package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Cidade;
import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.ModeloArrayAdapter;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.negocio.CidadeServices;
import br.com.projetoapp.sharepages.negocio.UsuarioServices;

public class CadastroUsuario extends Activity {

    private EditText textoNome, textoEmail, textoSenha;
    private Button botaoCadastrar;
    private Spinner cidadeSpinner;
    private UsuarioServices usuarioServices = UsuarioServices.getInstancia();
    private CidadeServices cidadeServices = CidadeServices.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        textoNome = (EditText) findViewById(R.id.textoNome);
        textoEmail = (EditText) findViewById(R.id.textoEmail);
        textoSenha = (EditText) findViewById(R.id.textoSenha);
        botaoCadastrar = (Button) findViewById(R.id.botaoCadastrar);

            adcCidadesNoSpinner();

            chamarBotaoCadastrar();

    }

    public void chamarBotaoCadastrar() {

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nome = textoNome.getText().toString().trim();
                String email = textoEmail.getText().toString().trim();
                String senha = textoSenha.getText().toString().trim();
                Cidade cidade = (Cidade) cidadeSpinner.getSelectedItem();

                Usuario usuario = new Usuario(nome, email, senha, cidade.getId());

                if (!validarCamposPreenchidos(usuario)) {
                    Toast.makeText(getApplication(), "Favor preencher todos os campos", Toast.LENGTH_LONG).show();

                }
                if (!validarEmail(email)) {
                    Toast.makeText(getApplication(), "Verifique o email", Toast.LENGTH_LONG).show();

                } else {
                    try {
                        cadastrarUsuario(usuario);
                    } catch (SharepagesException e) {
                        Toast.makeText(getApplication(),"Não cadastrou",Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
    }

    private void adcCidadesNoSpinner() {
        cidadeSpinner = (Spinner) findViewById(R.id.cidadeSpinner);

        ArrayList<Cidade> cidades = null;

        try {
            SessaoUsuario.getInstancia().setContext(this);
            cidades = cidadeServices.getCidades();
        } catch (SharepagesException e) {
            Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ModeloArrayAdapter<Cidade> dataAdapter = new ModeloArrayAdapter<Cidade>(this, android.R.layout.simple_spinner_item, cidades);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cidadeSpinner.setAdapter(dataAdapter);

        cidadeSpinner.getSelectedItem();

    }

    public boolean validarCamposPreenchidos(Usuario usuario) {
        boolean validacao = true;

        if (usuario.getNome() == null || usuario.getNome().equalsIgnoreCase("")) {
            validacao = false;
            textoNome.setError(getString(R.string.campo_obrigatorio));
        }
        if (usuario.getEmail() == null || usuario.getNome().equalsIgnoreCase("")) {
            validacao = false;
            textoEmail.setError(getString(R.string.campo_obrigatorio));

        }
        if (usuario.getSenha() == null || usuario.getSenha().equalsIgnoreCase("")) {
            validacao = false;
            textoSenha.setError(getString(R.string.campo_obrigatorio));

        }
        return validacao;
    }

    public boolean validarEmail(String email){
        boolean emailValido = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                emailValido = true;
            }
        }
        return emailValido;
    }

    public void cadastrarUsuario(Usuario usuario) throws SharepagesException {
        try {
            SessaoUsuario.getInstancia().setContext(this);
            usuarioServices.inserirUsuario(usuario);
            Toast.makeText(getApplication(),"Usuário cadastrado",Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e){
            Toast.makeText(getApplication(),"Usuário não cadastrado",Toast.LENGTH_LONG).show();
        }
    }

}