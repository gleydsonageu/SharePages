package br.com.projetoapp.sharepages.infra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Tema;


public class AdapterListTema extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Tema> itens;

    public AdapterListTema(Context context, List<Tema> itens){
        this.itens = itens;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Tema getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tema item = itens.get(position);

        String tema = item.getNome();
        convertView = inflater.inflate(R.layout.item_tema, null);
        ((TextView) convertView.findViewById(R.id.temaAdapterList)).setText(tema);

        return convertView;
    }
}
