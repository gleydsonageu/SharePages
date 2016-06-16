package br.com.projetoapp.sharepages.infra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        //Compoe o item com a posicao da Unidade livro
        UnidadeLivro item = itens.get(position);

        Livro livro = item.getLivro();
        String autor = item.getLivro().getAutor();
        Tema tema = item.getLivro().getTema();

        //carrega o layout com os dados
        convertView = inflater.inflate(R.layout.item_lista_livro, null);

        //compoe os TextView com as informacoes da UnidadeLivro relacionada
        ((TextView) convertView.findViewById(R.id.text)).setText(livro.getNome());
        ((TextView) convertView.findViewById(R.id.autor)).setText(autor);
        ((TextView) convertView.findViewById(R.id.tema)).setText(tema.getNome());

        return convertView;
    }
}