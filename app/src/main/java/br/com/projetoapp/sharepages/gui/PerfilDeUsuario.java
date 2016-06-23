package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Cidade;
import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.ModeloArrayAdapter;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.negocio.CidadeServices;
import br.com.projetoapp.sharepages.negocio.UsuarioServices;

public class PerfilDeUsuario extends Activity {

    private EditText textoNomePerfil, textoEmailPerfil, textoSenhaAtual, textoNovaSenha;
    private Button botaoAtualizar;
    private Spinner cidadeSpinner;

    CidadeServices cidadeServices = CidadeServices.getInstancia();
    UsuarioServices usuarioServices = UsuarioServices.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_de_usuario);

        textoNomePerfil = (EditText) findViewById(R.id.textoNomePerfil);
        textoEmailPerfil = (EditText) findViewById(R.id.textoEmailPerfil);
        textoSenhaAtual = (EditText) findViewById(R.id.textoSenhaAtual);
        textoNovaSenha = (EditText) findViewById(R.id.textoNovaSenha);
        cidadeSpinner = (Spinner) findViewById(R.id.cidadeSpinnerPerfil);
        botaoAtualizar = (Button) findViewById(R.id.botaoAtualizar);

        carregarPerfilUsuario();

        chamarBotaoAtualizar();

    }

    private void adcCidadesNoSpinner() throws SharepagesException {
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
    }

    public void selectCidadeSpinnerItemById(int id) {

        ModeloArrayAdapter<Cidade> adapter = (ModeloArrayAdapter<Cidade>) cidadeSpinner.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItem(position).getId() == id) {
                cidadeSpinner.setSelection(position);
            }
        }
    }

    public void carregarPerfilUsuario(){
        Usuario usuario = SessaoUsuario.getInstancia().getUsuarioLogado();

        textoNomePerfil.setText(usuario.getNome());
        textoEmailPerfil.setText(usuario.getEmail());
        textoEmailPerfil.setEnabled(false);
        try {
            adcCidadesNoSpinner();
            selectCidadeSpinnerItemById(usuario.getIdCidade());
        } catch (SharepagesException e) {
            Toast.makeText(getApplication(), "Houve algum problema no carregamento.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean validarCamposPreenchidos(Usuario usuario, String senhaAtual) {
        boolean validacao = true;

        if (usuario.getNome() == null || usuario.getNome().equalsIgnoreCase("")) {
            validacao = false;
            textoNomePerfil.setError(getString(R.string.campo_obrigatorio));
        }
        if (senhaAtual == null || senhaAtual.equalsIgnoreCase("")) {
            validacao = false;
            textoSenhaAtual.setError(getString(R.string.campo_obrigatorio));
        }
        return validacao;
    }

    public void chamarBotaoAtualizar (){
        botaoAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = textoNomePerfil.getText().toString().trim();
                String senha = textoNovaSenha.getText().toString().trim();
                Cidade cidade = (Cidade) cidadeSpinner.getSelectedItem();
                String senhaAtual = textoSenhaAtual.getText().toString().trim();

                Usuario usuario = new Usuario(nome, null, senha, cidade.getId());

                usuario.setId(SessaoUsuario.getInstancia().getUsuarioLogado().getId());

                if(!validarCamposPreenchidos(usuario, senhaAtual)){
                    Toast.makeText(getApplication(), "Favor preencher campos obrigatórios", Toast.LENGTH_LONG).show();
                }else try {
                    if (!usuarioServices.validarSenhaAtual(senhaAtual)){
                        Toast.makeText(getApplication(), "Senha atual invalida", Toast.LENGTH_LONG).show();

                    }else {
                        AlterarPerfilUsuarioLogado(usuario);
                    }
                } catch (UnsupportedEncodingException e) {
                    Toast.makeText(getApplication(), "Senha atual invalida", Toast.LENGTH_LONG).show();
                } catch (NoSuchAlgorithmException e) {
                    Toast.makeText(getApplication(), "Senha atual invalida", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void AlterarPerfilUsuarioLogado(Usuario usuario){

        try{
            usuarioServices.alterarPerfilUsuarioLogado(usuario);
            Toast.makeText(getApplication(), "Perfil atualizado", Toast.LENGTH_LONG).show();
            finish();
        } catch (SharepagesException e) {
            Toast.makeText(getApplication(),"Erro ao alterar usuario", Toast.LENGTH_LONG).show();
        } catch (NoSuchAlgorithmException e) {
            Toast.makeText(getApplication(),"Erro ao alterar usuario", Toast.LENGTH_LONG).show();
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(getApplication(),"Erro ao alterar usuario", Toast.LENGTH_LONG).show();

        }
    }

}