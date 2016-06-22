package br.com.projetoapp.sharepages.infra;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import br.com.projetoapp.sharepages.R;
import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.gui.ExclusaoUnidadeLivro;
import br.com.projetoapp.sharepages.gui.PerfilDeLivro;

public class AdapterListLivroPorUsuario extends BaseAdapter {

    private LayoutInflater inflater;
    private List<UnidadeLivro> itens;

    public AdapterListLivroPorUsuario(Context context, List<UnidadeLivro> itens){
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
        ImageButton excluirLivro;

        //carrega o layout com os dados
        convertView = inflater.inflate(R.layout.item_lista_livro_por_usuario, null);

        final View view = convertView;

        //compoe os TextView com as informacoes da UnidadeLivro relacionada
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
        excluirLivro = (ImageButton) convertView.findViewById(R.id.botaoExcluir);
        excluirLivro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ExclusaoUnidadeLivro.class);
                intent.putExtra("UnidadeLivro", item.getId());
                view.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}