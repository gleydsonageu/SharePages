package br.com.projetoapp.sharepages.gui;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.view.*;
import br.com.projetoapp.sharepages.R;
import android.content.*;

public class MenuPrincipal extends Activity implements View.OnClickListener{

    private Button editarPerfil;
    private Button cadastrarLivroMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        editarPerfil = (Button) findViewById(R.id.editarPerfil);
        cadastrarLivroMenu = (Button) findViewById(R.id.cadastrarLivroMenu);

        editarPerfil.setOnClickListener(this);
        cadastrarLivroMenu.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(v == cadastrarLivroMenu){
            Intent it = new Intent(this, CadastroLivro.class);
            startActivity(it);
        }



    }
}
