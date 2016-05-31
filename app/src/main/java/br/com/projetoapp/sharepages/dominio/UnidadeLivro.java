package br.com.projetoapp.sharepages.dominio;

public class UnidadeLivro {
    private int id;
    private int id_disponibilidade;
    private String descricao;
    private String idioma;
    private String edicao;
    private int numeroPaginas;
    private String editora;
    private Livro livro;
    private Foto foto;
    private Disponibilidade disponibilidade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getId_disponibilidade() {return id_disponibilidade;}

    public void setId_disponibilidade(int id_disponibilidade) {
        this.id_disponibilidade = id_disponibilidade;}
}
