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
import br.com.projetoapp.sharepages.infra.CidadeArrayAdapter;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.negocio.CidadeServices;
import br.com.projetoapp.sharepages.negocio.UsuarioServices;

public class CadastroUsuario extends Activity {

    private EditText textoNome, textoEmail, textoSenha;
    private Button botaoCadastrar;
    private Spinner cidadeSpinner;

    private UsuarioServices usuarioServices = UsuarioServices.getInstancia(this);
    private CidadeServices cidadeServices = CidadeServices.getInstancia(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        textoNome = (EditText) findViewById(R.id.textoNome);
        textoEmail = (EditText) findViewById(R.id.textoEmail);
        textoSenha = (EditText) findViewById(R.id.textoSenha);
        botaoCadastrar = (Button) findViewById(R.id.botaoCadastrar);

        //Preencher o spinner com as cidades
        try {
            adcCidadesNoSpinner();
        } catch (Exception e) {
            Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
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

                // /* chamada dos metodos validarCamposPreenchidos, validarEMail e cadastrar.
                // Gerar um carregamento de tela na confirmação da conta.
                // */
                if (!validarCamposPreenchidos(usuario)) {
                    Toast.makeText(getApplication(), "Favor preencher todos os campos", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!validarEmail(email)) {
                    Toast.makeText(getApplication(), "Verifique o email", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    try {
                        cadastrar(usuario);
                        Log.i("SCRIPT", "Chamando metodo para cadastrar nome " + nome);
                        Log.i("SCRIPT", "Chamando metodo para cadastrar email " + email);
                        Log.i("SCRIPT", "Chamando metodo para cadastrar senha " + senha);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    private void adcCidadesNoSpinner() throws SharepagesException {
        cidadeSpinner = (Spinner) findViewById(R.id.cidadeSpinner);

        ArrayList<Cidade> cidades = null;

        try {
            cidades = cidadeServices.pegarCidades();
        } catch (Exception e) {
            e.printStackTrace();
        }

        CidadeArrayAdapter dataAdapter = new CidadeArrayAdapter(this, android.R.layout.simple_spinner_item, cidades);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cidadeSpinner.setAdapter(dataAdapter);

        cidadeSpinner.getSelectedItem();

    }

    //validação de campos preenchidos
    public boolean validarCamposPreenchidos(Usuario usuario) {
        boolean validacao = true;
        Log.i("SCRIPT", "Chamada do metodo validar campos vazios ");
        if (usuario.getNome() == null || usuario.getNome().equalsIgnoreCase("")) {
            validacao = false;
            textoNome.setError(getString(R.string.campo_obrigatorio));
        }
        if (usuario.getEmail() == null || usuario.getNome().equalsIgnoreCase("")) {
            validacao = false;
            textoEmail.setError(getString(R.string.campo_obrigatorio));
        }
        if (usuario.getSenha() == null || usuario.getSenha().equalsIgnoreCase("")) {
            textoSenha.setError(getString(R.string.campo_obrigatorio));
        }
        return validacao;
    }

    // validação do padrão do email
    public boolean validarEmail(String email){
        boolean emailValido = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                emailValido = true;
                Log.i("SCRIPT", "email escrito correto");
            }
        }
        return emailValido;
    }

    //CADASTRAR usuario no banco
    public void cadastrar(Usuario usuario) {
        try {
            usuarioServices.inserirUsuario(usuario);
            Toast.makeText(getApplication(),"Usuário cadastrado",Toast.LENGTH_LONG).show();
            Log.i("SCRIPT", "Chamando metodo cadastrar ");
            finish();
        } catch (Exception e){
            Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

}