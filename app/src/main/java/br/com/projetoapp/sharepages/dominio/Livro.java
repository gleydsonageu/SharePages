package br.com.projetoapp.sharepages.dominio;


public class Livro {
    private int id;
    private String nome;
    private String autor;
    private Tema tema;
    private int idTema;

    public Livro(String nome, String autor, Tema tema, int idTema) {
        this.nome = nome;
        this.autor = autor;
        this.tema = tema;
        this.idTema = idTema;
    }

    public Livro(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }
}
