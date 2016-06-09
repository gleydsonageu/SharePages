package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Cidade;
import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.ModeloArrayAdapter;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.negocio.CidadeServices;
import br.com.projetoapp.sharepages.negocio.SessaoUsuario;
import br.com.projetoapp.sharepages.negocio.UsuarioServices;

public class PerfilDeUsuario extends Activity {

    private EditText textoNomePerfil, textoEmailPerfil, textoSenhaAtual, textoNovaSenha;
    private Button botaoAtualizar, botaoDeletar;
    private Spinner cidadeSpinner;

    CidadeServices cidadeServices = CidadeServices.getInstancia(this);
    UsuarioServices usuarioServices = UsuarioServices.getInstancia(this);

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
        botaoDeletar = (Button) findViewById(R.id.botaoDeletar);

        carregarPerfil();

        chamarBotaoAtualizar();

    }

    private void adcCidadesNoSpinner() throws SharepagesException {
        ArrayList<Cidade> cidades = null;

        try {
            cidades = cidadeServices.pegarCidades();
        } catch (Exception e) {
            e.printStackTrace();
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
                return;
            }
        }
    }

    public boolean validarCamposPreenchidos(Usuario usuario) {
        boolean validacao = true;

        Log.i("SCRIPT", "Chamada do metodo validar campos vazios ");
        if (usuario.getNome() == null || usuario.getNome().equalsIgnoreCase("")) {
            validacao = false;
            textoNomePerfil.setError(getString(R.string.campo_obrigatorio));
        }
        if (usuario.getSenha() == null || usuario.getSenha().equalsIgnoreCase("")) {
            textoNovaSenha.setError(getString(R.string.campo_obrigatorio));
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

                Usuario usuario = new Usuario(nome, null, senha, cidade.getId());
                usuario.setId(SessaoUsuario.getInstancia().getUsuarioLogado().getId());

                if(!validarCamposPreenchidos(usuario)){
                    Toast.makeText(getApplication(), "Favor preencher todos os campos", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    try{
                        usuarioServices.alterarUsuarioLogado(usuario);

                    } catch (SharepagesException e) {
                        Toast.makeText(getApplication(),"erro ao alterar usuario", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void carregarPerfil(){
        Intent intent = getIntent();
        if (intent != null) {
            Usuario usuario = SessaoUsuario.getInstancia().getUsuarioLogado();

            textoNomePerfil.setText(usuario.getNome());
            textoEmailPerfil.setText(usuario.getEmail());
            textoEmailPerfil.setEnabled(false);
            try {

                adcCidadesNoSpinner();
                selectCidadeSpinnerItemById(usuario.getIdCidade());
            } catch (SharepagesException e) {
                e.printStackTrace();
            }
        }
    }

}