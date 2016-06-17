
package br.com.projetoapp.sharepages.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;

public class SplashScreen extends Activity {

    private final int DURACAO_DA_TELA = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SessaoUsuario.getInstancia().setContext(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent chamarTelaInicial = new Intent(SplashScreen.this, TelaInicial.class);

                SplashScreen.this.startActivity(chamarTelaInicial);
                SplashScreen.this.finish();
            }
        }, DURACAO_DA_TELA);
    }
}