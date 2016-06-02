package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.projetoapp.sharepages.R;

public class MenuPrincipal extends Activity{

    private Button botaoEditarPerfil;
    private Button botaoCadastrarLivro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        botaoEditarPerfil = (Button) findViewById(R.id.botaoEditarPerfil);
        botaoCadastrarLivro = (Button) findViewById(R.id.botaoCadastrarLivro);



        botaoEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, PerfilDeUsuario.class);
                startActivity(intent);
            }
        });

        botaoCadastrarLivro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuPrincipal.this, CadastroLivro.class);
                startActivity(it);
            }
        });

    }
}
