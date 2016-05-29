package br.com.projetoapp.sharepages.gui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Cidade;

/**
 * Created by airton on 29/05/16.
 */
public class CidadeArrayAdapter extends ArrayAdapter<Cidade> {

    private Context context;
    private ArrayList<Cidade> values;

    public CidadeArrayAdapter(Context context, int textViewResourceId, ArrayList<Cidade> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.size();
    }

    public Cidade getItem(int posicao){
        return values.get(posicao);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(posicao).getNome());
        return label;
    }

    @Override
    public View getDropDownView(int posicao, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(posicao).getNome());

        return label;
    }
}
