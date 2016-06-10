package br.com.projetoapp.sharepages.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.projetoapp.sharepages.dominio.Usuario;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 22;
    public static final String DATABASE = "meubanco.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    //Criando tabela CidadeDAO
    public static final String TABLE_CIDADES = "Cidades";

    public static final String CIDADE_ID = "id";
    public static final String CIDADE_NOME = "nome";
    public static final String[] CIDADE_COLUNAS = {CIDADE_ID , CIDADE_NOME };


    private static final String DATABASE_CIDADE = "CREATE TABLE " + TABLE_CIDADES + "(" +
            CIDADE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            CIDADE_NOME + " TEXT NOT NULL)";

    //Criando a tabela usuarioDAO
    public static final String TABLE_USUARIOS = "usuarios";

    public static final String USUARIO_ID = "id";
    public static final String USUARIO_NOME = "nome";
    public static final String USUARIO_EMAIL = "email";
    public static final String USUARIO_SENHA = "senha";
    public static final String USUARIO_ID_CIDADE = "idCidade";
    public static final String[] USUARIO_COLUNAS = { USUARIO_ID, USUARIO_NOME, USUARIO_EMAIL, USUARIO_SENHA, USUARIO_ID_CIDADE };

    private static final String DATABASE_USUARIO = "CREATE TABLE " + TABLE_USUARIOS + "(" +
            USUARIO_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            USUARIO_NOME +" TEXT NOT NULL, "+
            USUARIO_EMAIL + " TEXT NOT NULL UNIQUE, "+
            USUARIO_SENHA + " TEXT NOT NULL, "+
            USUARIO_ID_CIDADE + " TEXT NOT NULL, " +
            "FOREIGN KEY(" + USUARIO_ID_CIDADE + ") REFERENCES " + TABLE_CIDADES + "(" + CIDADE_ID + "))";

    //Criando tabela TemaDAO
    public static final String TABLE_TEMAS = "temas";

    public static final String TEMAS_ID = "id";
    public static final String TEMAS_NOME = "nome";
    public static final String[] TEMA_COLUNAS = {TEMAS_ID, TEMAS_NOME};

    private static final String DATABASE_TEMA = "CREATE TABLE " + TABLE_TEMAS + "(" +
            TEMAS_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
            TEMAS_NOME + " TEXT NOT NULL)";



    //Criando a tabela LivroDAO
    public static final String TABLE_LIVRO = "livros";
    public static final String LIVRO_ID = "id";
    public static final String LIVRO_NOME = "nome";
    public static final String LIVRO_AUTOR = "autor";
    public static final String LIVRO_ID_TEMA = "idTema";

    public static final String[] LIVRO_COLUNAS = {LIVRO_ID, LIVRO_NOME, LIVRO_AUTOR, LIVRO_ID_TEMA};

    private static final String DATABASE_LIVRO = "CREATE TABLE " + TABLE_LIVRO + "(" +
            LIVRO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            LIVRO_NOME + " TEXT NOT NULL, "+
            LIVRO_AUTOR + " TEXT NOT NULL, " +
            LIVRO_ID_TEMA + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + LIVRO_ID_TEMA + ") REFERENCES " + TABLE_TEMAS + "(" + TEMAS_ID + "))";


    //Criando tabela DisponibilidadeDAO
    public static final String TABLE_DISPONIBILIDADES = "disponibilidades";

    public static final String DISPONIBILIDADE_ID = "id";
    public static final String DISPONIBILIDADE_NOME = "nome";
    public static final String[] DISPONIBILIDADE_COLUNAS = {DISPONIBILIDADE_ID, DISPONIBILIDADE_NOME};

    private static final String DATABASE_DISPONIBILIDADE = "CREATE TABLE " + TABLE_DISPONIBILIDADES + "(" +
            DISPONIBILIDADE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
            DISPONIBILIDADE_NOME + " TEXT NOT NULL)";



    // Criando a tabela UnidadeLivroDAO
    public static final String TABLE_UNIDADELIVROS = "UnidadeLivros";

    public static final String UNIDADELIVRO_ID = "id";
    public static final String UNIDADELIVRO_DESCRICAO = "descricao";
    public static final String UNIDADELIVRO_IDIOMA = "idioma";
    public static final String UNIDADELIVRO_EDICAO = "edicao";
    public static final String UNIDADELIVRO_NUMEROPAGINAS = "numeroPaginas";
    public static final String UNIDADELIVRO_EDITORA = "editora";
    public static final String UNIDADELIVRO_ID_LIVRO = "idLivro";
    public static final String UNIDADELIVRO_ID_DISPONIBILIDADE = "idDisponibilidade";
    public static final String UNIDADELIVRO_ID_USUARIO = "idUsuario";

    public static final String[] UNIDADELIVRO_COLUNAS = { UNIDADELIVRO_ID, UNIDADELIVRO_DESCRICAO, UNIDADELIVRO_IDIOMA, UNIDADELIVRO_EDICAO,
            UNIDADELIVRO_NUMEROPAGINAS, UNIDADELIVRO_EDITORA, UNIDADELIVRO_ID_LIVRO, UNIDADELIVRO_ID_DISPONIBILIDADE,
            UNIDADELIVRO_ID_USUARIO};

    private static final String DATABASE_UNIDADELIVRO = "CREATE TABLE " + TABLE_UNIDADELIVROS + "(" +
            UNIDADELIVRO_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            UNIDADELIVRO_DESCRICAO + " TEXT NOT NULL, " +
            UNIDADELIVRO_IDIOMA +" TEXT NOT NULL, "+
            UNIDADELIVRO_EDICAO + " TEXT NOT NULL, "+
            UNIDADELIVRO_NUMEROPAGINAS + " INTEGER NOT NULL, "+
            UNIDADELIVRO_EDITORA + " TEXT NOT NULL, " +
            UNIDADELIVRO_ID_LIVRO + " INTEGER NOT NULL, " +
            UNIDADELIVRO_ID_DISPONIBILIDADE + " INTEGER NOT NULL, " +
            UNIDADELIVRO_ID_USUARIO + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + UNIDADELIVRO_ID_LIVRO + ") REFERENCES " + TABLE_LIVRO + "(" + LIVRO_ID + "), " +
            "FOREIGN KEY(" + UNIDADELIVRO_ID_DISPONIBILIDADE + ") REFERENCES " + TABLE_DISPONIBILIDADES + "(" + DISPONIBILIDADE_ID + "), " +
            "FOREIGN KEY(" + UNIDADELIVRO_ID_USUARIO + ") REFERENCES " + TABLE_USUARIOS + "(" + USUARIO_ID + "))";


    //Criando tabela FotoDAO
    public static final String TABLE_FOTOS = "fotos";

    public static final String FOTO_ID = "id";
    public static final String FOTO_CAMINHO = "caminho";
    public static final String FOTO_ID_UNIDADELIVRO = "idUnidadeLivro";
    public static final String[] FOTO_COLUNAS = {FOTO_ID, FOTO_CAMINHO, FOTO_ID_UNIDADELIVRO};

    private static final String DATABASE_FOTO = "CREATE TABLE " + TABLE_FOTOS + "(" +
            FOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FOTO_CAMINHO + " TEXT NOT NULL, " +
            FOTO_ID_UNIDADELIVRO + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + FOTO_ID_UNIDADELIVRO + ") REFERENCES " + TABLE_UNIDADELIVROS + "(" + UNIDADELIVRO_ID + "))";



    public void listaDisponibilidade(SQLiteDatabase database) {
        String nomeDisponibilidades[] = {"Doação", "Troca"};
        for (String nomeDisponibilidade : nomeDisponibilidades) {
            inserirDisponibilidade(database, nomeDisponibilidade);
        }
    }

    public void listaTemas(SQLiteDatabase database){
        String nomeTemas[] = {"Romântico", "Ficção Cientifíca", "Terror", "Guerra"};
        for (String nomeTema : nomeTemas) {
            inserirTema(database, nomeTema);
        }
    }

    public void listaCidades(SQLiteDatabase database) {
        String nomeCidades[] = {"Jaboatão", "Olinda", "Recife"};
        for (String nomeCidade : nomeCidades) {
            inserirCidade(database, nomeCidade);
        }
    }

    public void criarUsuarioDefault (SQLiteDatabase database) {
        Usuario usuario = new Usuario();
        usuario.setNome("Joao");
        usuario.setEmail("joao@gmail.com");
        usuario.setSenha("123456");
        usuario.setIdCidade(1);
        inserirUsuario(database, usuario);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_USUARIO);
        database.execSQL(DATABASE_UNIDADELIVRO);
        database.execSQL(DATABASE_CIDADE);
        database.execSQL(DATABASE_DISPONIBILIDADE);
        database.execSQL(DATABASE_TEMA);
        database.execSQL(DATABASE_LIVRO);
        database.execSQL(DATABASE_FOTO);

        //Lista para inserir disponibilidades
        listaDisponibilidade(database);

        //Lista para inserir Temas
        listaTemas(database);

        //Lista para inserir cidades
        listaCidades(database);

        //criando usuario para inserir no banco
        criarUsuarioDefault(database);

    }

    public void inserirUsuario(SQLiteDatabase database, Usuario usuario)  {

        ContentValues content = new ContentValues();
        content.put(USUARIO_NOME, usuario.getNome());
        content.put(USUARIO_EMAIL, usuario.getEmail());
        content.put(USUARIO_SENHA, usuario.getSenha());
        content.put(USUARIO_ID_CIDADE, usuario.getIdCidade());

        database.insert(TABLE_USUARIOS, null, content);
    }


    public void inserirCidade(SQLiteDatabase database, String nomeCidade) {
        ContentValues values = new ContentValues();
        values.put(CIDADE_NOME, nomeCidade);
        database.insert(TABLE_CIDADES, null, values);
    }

    public void inserirDisponibilidade(SQLiteDatabase database, String nomeDisponibilidade) {
        ContentValues values = new ContentValues();
        values.put(DISPONIBILIDADE_NOME, nomeDisponibilidade);
        database.insert(TABLE_DISPONIBILIDADES, null, values);
    }

    public void inserirTema(SQLiteDatabase database, String nomeTema) {
        ContentValues values = new ContentValues();
        values.put(TEMAS_NOME, nomeTema);
        database.insert(TABLE_TEMAS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_UNIDADELIVROS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CIDADES);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_DISPONIBILIDADES);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TEMAS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_LIVRO);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_FOTOS);
        onCreate(db);
    }

}