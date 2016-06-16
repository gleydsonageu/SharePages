package br.com.projetoapp.sharepages.infra;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Foto;
import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.gui.MinhaPrateleira;
import br.com.projetoapp.sharepages.gui.PerfilDeLivro;

public class AdapterListLivro extends BaseAdapter {

    private LayoutInflater inflater;
    private List<UnidadeLivro> itens;

    public AdapterListLivro(Context context, List<UnidadeLivro> itens){
        this.itens = itens;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public UnidadeLivro getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        //Compoe o item com a posicao da Unidade livro
        final UnidadeLivro item = itens.get(position);

        Livro livro = item.getLivro();
        String autor = item.getLivro().getAutor();
        Tema tema = item.getLivro().getTema();
       // Foto foto = item.getUnidadeLivro().getCaminho();
        ImageButton editarLivro;

        //carrega o layout com os dados
        convertView = inflater.inflate(R.layout.item_lista_livro, null);

        final View view = convertView;


        //compoe os TextView com as informacoes da UnidadeLivro relacionada
        Log.d("grrr--", livro.getNome() + " " + livro.getAutor() + " " + livro.getTema());
        ((TextView) convertView.findViewById(R.id.livro)).setText(livro.getNome());
        ((TextView) convertView.findViewById(R.id.autor)).setText(autor);
        ((TextView) convertView.findViewById(R.id.tema)).setText(tema.getNome());
        editarLivro = (ImageButton) convertView.findViewById(R.id.editarLivro);
        editarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), PerfilDeLivro.class);
                intent.putExtra("UnidadeLivro", item.getId());
                view.getContext().startActivity(intent);
            }
        });


        return convertView;
    }
}