package br.com.projetoapp.sharepages.infra;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.projetoapp.sharepages.dominio.Modelo;

public class ModeloArrayAdapter<T extends Modelo> extends ArrayAdapter<T> {

    private Context context;
    private ArrayList<T> values;

    public ModeloArrayAdapter(Context context, int textViewResourceId, ArrayList<T> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.size();
    }

    public T getItem(int posicao){
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
