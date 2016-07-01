package br.com.projetoapp.sharepages.gui;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.projetoapp.sharepages.R;

public class EnviarEmail extends Activity {

    private EditText campoEmailEnviar = null;
    private EditText campoAssunto = null;
    private EditText corpoEmail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_email);
        String emailDoAnuncio = getIntent().getStringExtra("EmailUsuario");

        campoEmailEnviar = (EditText) findViewById(R.id.campoEmailEnviar);
        campoAssunto = (EditText) findViewById(R.id.campoAssunto);
        corpoEmail = (EditText) findViewById(R.id.corpoMail);

        campoEmailEnviar.setText(emailDoAnuncio);
        campoEmailEnviar.setEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_enviar_email, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                campoEmailEnviar.setText("");
                corpoEmail.setText("");
                campoAssunto.setText("");
                break;
            case R.id.menu_send:
                String para = campoEmailEnviar.getText().toString();
                String assunto = campoAssunto.getText().toString();
                String mensagem = corpoEmail.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[] { para });
                email.putExtra(Intent.EXTRA_SUBJECT, assunto);
                email.putExtra(Intent.EXTRA_TEXT, mensagem);

                // need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Escolha um cliente de e-mail"));

                break;
        }
        return true;
    }
}