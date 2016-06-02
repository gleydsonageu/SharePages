package br.com.projetoapp.sharepages.dominio;


public class Tema {
    private int id;
    private String nome;

    public String getNome() {return nome;}

    public void setNome(String tipo) {
        this.nome = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
