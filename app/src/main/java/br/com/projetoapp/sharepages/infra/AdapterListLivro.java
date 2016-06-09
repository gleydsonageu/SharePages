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

/**
 * Created by gleydson on 09/06/16.
 */

public class AdapterListLivro extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Livro> itens;

    public AdapterListLivro(Context context, List<Livro> itens){
        this.itens = itens;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Livro getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Livro item = itens.get(position);

        convertView = inflater.inflate(R.layout.item_lista_livro, null);

        ((TextView) convertView.findViewById(R.id.text)).setText(item.getNome());

        return convertView;
    }
}
