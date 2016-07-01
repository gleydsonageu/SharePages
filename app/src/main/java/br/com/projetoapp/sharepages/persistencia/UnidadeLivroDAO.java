package br.com.projetoapp.sharepages.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.projetoapp.sharepages.dominio.Cidade;
import br.com.projetoapp.sharepages.dominio.Disponibilidade;
import br.com.projetoapp.sharepages.dominio.Livro;
import br.com.projetoapp.sharepages.dominio.Tema;
import br.com.projetoapp.sharepages.dominio.UnidadeLivro;
import br.com.projetoapp.sharepages.dominio.Usuario;
import br.com.projetoapp.sharepages.infra.SessaoUsuario;
import br.com.projetoapp.sharepages.infra.SharepagesException;

/**
 * Classe de acesso a Tabela Unidade Livro no DatabaseHelper.
 */
public class UnidadeLivroDAO {

    public static UnidadeLivroDAO getInstacia(){
        UnidadeLivroDAO instancia = new UnidadeLivroDAO();
        return instancia;
    }

    /**
     * Metodo para escrever a unidade livro no banco DatabaseHelper, as informções de Unidade Livro: descrição, idioma,
     * edição, numero de paginas, editora, ID do Livro, ID de Usuario, ID da disponibilidade.
     * @param unidadeLivro
     * @return retorna a inserção da unidade livro no banco.
     * @throws SharepagesException
     */
    public long inserirUnidadeLivro(UnidadeLivro unidadeLivro) throws SharepagesException {

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        try (SQLiteDatabase database = databaseHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.UNIDADELIVRO_DESCRICAO, unidadeLivro.getDescricao());
            values.put(DatabaseHelper.UNIDADELIVRO_IDIOMA, unidadeLivro.getIdioma());
            values.put(DatabaseHelper.UNIDADELIVRO_EDICAO, unidadeLivro.getEdicao());
            values.put(DatabaseHelper.UNIDADELIVRO_NUMEROPAGINAS, unidadeLivro.getNumeroPaginas());
            values.put(DatabaseHelper.UNIDADELIVRO_EDITORA, unidadeLivro.getEditora());
            values.put(DatabaseHelper.UNIDADELIVRO_ID_LIVRO, unidadeLivro.getIdLivro());
            values.put(DatabaseHelper.UNIDADELIVRO_ID_USUARIO, unidadeLivro.getIdUsuario());
            values.put(DatabaseHelper.UNIDADELIVRO_ID_DISPONIBILIDADE, unidadeLivro.getIdDisponibilidade());

            long retorno = database.insert(DatabaseHelper.TABLE_UNIDADELIVROS, null, values);
            database.close();
            return retorno;
        }
    }

    /**
     * Na Tabela Unidade Livro permite a alteração da coluna descrição e a disponibilidade do livro.
     * @param unidadeLivro
     * @return
     * @throws SharepagesException
     */
    public long alterarUnidadeLivro(UnidadeLivro unidadeLivro) throws SharepagesException {
        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.UNIDADELIVRO_DESCRICAO, unidadeLivro.getDescricao());
        values.put(DatabaseHelper.UNIDADELIVRO_ID_DISPONIBILIDADE, unidadeLivro.getIdDisponibilidade());

        String id = String.valueOf(unidadeLivro.getId());
        String[] whereArgs = new String[]{id};
        long retorno = database.update(DatabaseHelper.TABLE_UNIDADELIVROS, values, "id = ?", whereArgs);
        database.close();
        return retorno;
    }

    /**
     * busca o livro pelo ID do usuario logado.
     * @param id
     * @return lista de livros do usuario
     */
    public List<UnidadeLivro> buscarLivroPorIdUsuario(int id){
        UnidadeLivro unidadeLivro = null;

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        List<UnidadeLivro> listUnidadeLivro = new ArrayList<UnidadeLivro>();

        Cursor cursor = database.rawQuery(parteDaConsulta()
                +" WHERE "+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_USUARIO+ " = ? AND " +DatabaseHelper.TABLE_UNIDADELIVROS+"."+ DatabaseHelper.UNIDADELIVRO_SITUACAO+ " IS NULL;", new String[]{String.valueOf(id)});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            unidadeLivro = carregandoUnidadeLivro(cursor);
            listUnidadeLivro.add(unidadeLivro);
            cursor.moveToNext();
        }
        database.close();
        return listUnidadeLivro;
    }

    /**
     * Busca Unidade livro por ID.
     * @param id
     * @return unidade livro
     */
    public UnidadeLivro buscarLivroPorId(int id){
        UnidadeLivro unidadeLivro = null;

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery(parteDaConsulta()
                +" WHERE "+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID+ " = ? AND " +DatabaseHelper.TABLE_UNIDADELIVROS+"."+ DatabaseHelper.UNIDADELIVRO_SITUACAO+ " IS NULL;", new String[]{String.valueOf(id)});

        cursor.moveToFirst();
        unidadeLivro = carregandoUnidadeLivro(cursor);
        database.close();
        return unidadeLivro;
    }

    /**
     * Busca livros por tema.
     * @param id
     * @param idUsuarioLogado
     * @return lista de livros pelo tema.
     */
    public List<UnidadeLivro> buscarLivroPorTema(int id, int idUsuarioLogado){
        UnidadeLivro unidadeLivro = null;

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        List<UnidadeLivro> listLivroPorTema = new ArrayList<UnidadeLivro>();

        Cursor cursor = database.rawQuery(parteDaConsulta()
                +" WHERE "+DatabaseHelper.TABLE_TEMAS+"."+DatabaseHelper.TEMAS_ID
                + " = ?AND " +DatabaseHelper.TABLE_UNIDADELIVROS+"."+ DatabaseHelper.UNIDADELIVRO_SITUACAO + " IS NULL"
                +" AND "+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_USUARIO + " != ?;",
                new String[]{String.valueOf(id), String.valueOf(idUsuarioLogado)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            unidadeLivro = carregandoUnidadeLivro(cursor);
            listLivroPorTema.add(unidadeLivro);
            cursor.moveToNext();
        }
        database.close();
        return listLivroPorTema;
    }

    /**
     * Busca livros por nome e autor.
     * @param nome
     * @param idUsuarioLogado
     * @return lista de livros
     */
    public List<UnidadeLivro> buscarLivroPorNomeOuAutor(String nome, int idUsuarioLogado){
        UnidadeLivro unidadeLivro = null;

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        List<UnidadeLivro> listLivro = new ArrayList<UnidadeLivro>();

        Cursor cursor = database.rawQuery(parteDaConsulta()
                +" WHERE ("+DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_NOME+" LIKE ?"
                +" OR "+DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_AUTOR+" LIKE ? )"
                +" AND "+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_SITUACAO+" IS NULL"
                +" AND "+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_USUARIO+ " != ?;",
                new String[]{"%"+nome+"%", "%"+nome+"%",String.valueOf(idUsuarioLogado)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            unidadeLivro = carregandoUnidadeLivro(cursor);
            listLivro.add(unidadeLivro);
            cursor.moveToNext();
        }
        database.close();
        return listLivro;
    }

    /**
     * Altera a situação do livro na Tabela Unidade livro.
     * @param unidadeLivro
     * @return
     */
    public boolean alterarSituacao(UnidadeLivro unidadeLivro) {
        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        DatabaseHelper databaseHelper = new DatabaseHelper(sessaoUsuario.getContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.UNIDADELIVRO_SITUACAO, unidadeLivro.getSituacao().getNome());

        String id = String.valueOf(unidadeLivro.getId());
        String[] whereArgs = new String[]{id};
        long retorno = database.update(DatabaseHelper.TABLE_UNIDADELIVROS, values, "id = ?", whereArgs);
        database.close();

        return retorno == 1;
    }

    /**
     * @param cursor cursor para carregar o objeto Unidade livro
     * @return unidLivro
     */
    public UnidadeLivro carregandoUnidadeLivro(Cursor cursor){
        UnidadeLivro unidLivro = null;

        unidLivro = new UnidadeLivro();
        unidLivro.setId(cursor.getInt(0));
        unidLivro.setDescricao(cursor.getString(1));
        unidLivro.setIdioma(cursor.getString(2));
        unidLivro.setEdicao(cursor.getString(3));
        unidLivro.setNumeroPaginas(cursor.getInt(4));
        unidLivro.setEditora(cursor.getString(5));
        unidLivro.setIdLivro(cursor.getInt(6));
        unidLivro.setIdDisponibilidade(cursor.getInt(7));
        unidLivro.setIdUsuario(cursor.getInt(8));
        Livro livro = new Livro();
        livro.setId(cursor.getInt(9));
        livro.setNome(cursor.getString(10));
        livro.setAutor(cursor.getString(11));
        livro.setIdTema(cursor.getInt(12));
        Tema  tema = new Tema();
        tema.setId(cursor.getInt(13));
        tema.setNome(cursor.getString(14));
        Disponibilidade disponibilidade = new Disponibilidade();
        disponibilidade.setId(cursor.getInt(15));
        disponibilidade.setNome(cursor.getString(16));
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getInt(17));
        usuario.setIdCidade(cursor.getInt(18));
        usuario.setEmail(cursor.getString(21));
        unidLivro.setUsuario(usuario);
        Cidade cidade = new Cidade();
        cidade.setId(cursor.getInt(19));
        cidade.setNome(cursor.getString(20));
        usuario.setCidade(cidade);

        unidLivro.setFotos(FotoDAO.getInstancia().buscarFotosPorIdUnidadeLivro(unidLivro.getId()));

        unidLivro.setLivro(livro);
        livro.setTema(tema);
        unidLivro.setDisponibilidade(disponibilidade);
        return unidLivro;
    }

    /**
     * String para a consulta no banco SQL do metodos anteriores.
     * @return a consulta
     */
    public String parteDaConsulta() {
        String consulta = " SELECT " + DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID+ ", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_DESCRICAO+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_IDIOMA+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_EDICAO+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_NUMEROPAGINAS+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_EDITORA+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_LIVRO+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_DISPONIBILIDADE+", "
                +DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_USUARIO+", "
                +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID+", "
                +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_NOME+", "
                +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_AUTOR+", "
                +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID_TEMA+", "
                +DatabaseHelper.TABLE_TEMAS+"."+DatabaseHelper.TEMAS_ID+", "
                +DatabaseHelper.TABLE_TEMAS+"."+DatabaseHelper.TEMAS_NOME+", "
                +DatabaseHelper.TABLE_DISPONIBILIDADES+"."+DatabaseHelper.DISPONIBILIDADE_ID+", "
                +DatabaseHelper.TABLE_DISPONIBILIDADES+"."+DatabaseHelper.DISPONIBILIDADE_NOME+", "
                +DatabaseHelper.TABLE_USUARIOS+"."+DatabaseHelper.USUARIO_ID+", "
                +DatabaseHelper.TABLE_USUARIOS+"."+DatabaseHelper.USUARIO_ID_CIDADE+", "
                +DatabaseHelper.TABLE_CIDADES+"."+DatabaseHelper.CIDADE_ID+", "
                +DatabaseHelper.TABLE_CIDADES+"."+DatabaseHelper.CIDADE_NOME+", "
                +DatabaseHelper.TABLE_USUARIOS+"."+DatabaseHelper.USUARIO_EMAIL
                +" FROM "+DatabaseHelper.TABLE_UNIDADELIVROS +" INNER JOIN "+ DatabaseHelper.TABLE_LIVRO
                +" ON ("+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_LIVRO+ " = " +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_TEMAS
                +" ON (" +DatabaseHelper.TABLE_LIVRO+"."+DatabaseHelper.LIVRO_ID_TEMA+ " = " +DatabaseHelper.TABLE_TEMAS +"."+ DatabaseHelper.TEMAS_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_DISPONIBILIDADES
                +" ON ("+ DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_DISPONIBILIDADE+ " = " +DatabaseHelper.TABLE_DISPONIBILIDADES+ "." +DatabaseHelper.DISPONIBILIDADE_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_USUARIOS
                +" ON ("+DatabaseHelper.TABLE_UNIDADELIVROS+"."+DatabaseHelper.UNIDADELIVRO_ID_USUARIO+ " = " +DatabaseHelper.TABLE_USUARIOS+ "." +DatabaseHelper.USUARIO_ID
                +") INNER JOIN " +DatabaseHelper.TABLE_CIDADES
                +" ON (" +DatabaseHelper.TABLE_USUARIOS+"."+DatabaseHelper.USUARIO_ID_CIDADE+ " = " +DatabaseHelper.TABLE_CIDADES+"."+DatabaseHelper.CIDADE_ID+ ")";

        return consulta;
    }

}