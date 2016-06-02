package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.projetoapp.sharepages.R;

public class MenuPrincipal extends Activity implements View.OnClickListener{

    private Button botaoEditarPerfil;
    private Button cadastrarLivroMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        botaoEditarPerfil = (Button) findViewById(R.id.botaoEditarPerfil);

        botaoEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, PerfilDeUsuario.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {

        if(v == cadastrarLivroMenu){
            Intent it = new Intent(this, CadastroLivro.class);
            startActivity(it);
        }



    }
}
