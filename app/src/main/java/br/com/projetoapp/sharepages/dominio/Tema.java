package br.com.projetoapp.sharepages.dominio;


public class Tema {
    private int id;
    private String tema;

    public String getTema() {return tema;}

    public void setTema(String tipo) {
        this.tema = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
