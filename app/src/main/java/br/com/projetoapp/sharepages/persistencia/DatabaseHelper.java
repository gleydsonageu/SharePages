package br.com.projetoapp.sharepages.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.projetoapp.sharepages.dominio.Usuario;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 13;
    public static final String DATABASE = "meubanco.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

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
            USUARIO_ID_CIDADE + " TEXT NOT NULL)";



    // Criando a tabela UnidadeLivroDAO
    public static final String TABLE_UNIDADELIVROS = "UnidadeLivros";

    public static final String UNIDADELIVRO_ID = "id";
    public static final String UNIDADELIVRO_DESCRICAO = "descricao";
    public static final String UNIDADELIVRO_IDIOMA = "idioma";
    public static final String UNIDADELIVRO_EDICAO = "edicao";
    public static final String UNIDADELIVRO_NUMEROPAGINAS = "numeroPaginas";
    public static final String UNIDADELIVRO_EDITORA = "editora";
    public static final String[] UNIDADELIVRO_COLUNAS = { UNIDADELIVRO_ID, UNIDADELIVRO_DESCRICAO, UNIDADELIVRO_IDIOMA, UNIDADELIVRO_EDICAO,
            UNIDADELIVRO_NUMEROPAGINAS, UNIDADELIVRO_EDITORA };

    private static final String DATABASE_UNIDADELIVRO = "CREATE TABLE " + TABLE_UNIDADELIVROS + "(" +
            UNIDADELIVRO_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            UNIDADELIVRO_DESCRICAO + " TEXT NOT NULL, " +
            UNIDADELIVRO_IDIOMA +" TEXT NOT NULL, "+
            UNIDADELIVRO_EDICAO + " TEXT NOT NULL, "+
            UNIDADELIVRO_NUMEROPAGINAS + " INTEGER NOT NULL, "+
            UNIDADELIVRO_EDITORA + " TEXT NOT NULL)";



    //Criando tabela CidadeDAO
    public static final String TABLE_CIDADES = "Cidades";

    public static final String CIDADE_ID = "id";
    public static final String CIDADE_NOME = "nome";
    public static final String[] CIDADE_COLUNAS = {CIDADE_ID , CIDADE_NOME };


    private static final String DATABASE_CIDADE = "CREATE TABLE " + TABLE_CIDADES + "(" +
            CIDADE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            CIDADE_NOME + " TEXT NOT NULL)";


    //Criando tabela DisponibilidadeDAO
    public static final String TABLE_DISPONIBILIDADES = "disponibilidades";

    public static final String DISPONIBILIDADE_ID = "id";
    public static final String DISPONIBILIDADE_NOME = "nome";
    public static final String[] DISPONIBILIDADE_COLUNAS = {DISPONIBILIDADE_ID, DISPONIBILIDADE_NOME};

    private static final String DATABASE_DISPONIBILIDADE = "CREATE TABLE " + TABLE_DISPONIBILIDADES + "(" +
            DISPONIBILIDADE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
            DISPONIBILIDADE_NOME + " TEXT NOT NULL)";


    //Criando tabela TemaDAO
    public static final String TABLE_TEMAS = "temas";

    public static final String TEMAS_ID = "id";
    public static final String TEMAS_NOME = "nome";
    public static final String[] TEMA_COLUNAS = {TEMAS_ID, TEMAS_NOME};

    private static final String DATABASE_TEMA = "CREATE TABLE " + TABLE_TEMAS + "(" +
            TEMAS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_TEMAS + " TEXT NOT NULL)";







    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_USUARIO);
        database.execSQL(DATABASE_UNIDADELIVRO);
        database.execSQL(DATABASE_CIDADE);
        database.execSQL(DATABASE_DISPONIBILIDADE);
        database.execSQL(DATABASE_TEMA);


        //Lista para inserir disponibilidades
        String nomeDisponibilidades[] = {"Doacao", "Troca"};
        for (String nomeDisponibilidade : nomeDisponibilidades) {
            inserirDisponibilidade(database, nomeDisponibilidade);
        }

        //Lista para inserir Temas
        String nomeTemas[] = {"Romantico", "Ficção Cientifíca", "Terror", "Guerra"};
        for(String nomeTema : nomeTemas) {
            inserirTema(database, nomeTema);
        }



        //Lista para inserir cidades
        String nomeCidades[] = { "Jaboatao", "Olinda", "Recife" };
        for(String nomeCidade : nomeCidades) {
            inserirCidade(database, nomeCidade);
        }

        //criando usuario para inserir no banco
        Usuario usuario = new Usuario();
        usuario.setNome("Joao");
        usuario.setEmail("joao@gmail.com");
        usuario.setSenha("123456");
        usuario.setIdCidade(1);
        inserirUsuario(database, usuario);
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
        onCreate(db);
    }
}