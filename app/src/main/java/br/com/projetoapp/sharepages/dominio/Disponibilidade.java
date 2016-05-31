package br.com.projetoapp.sharepages.dominio;


public class Disponibilidade {

    private int id;
    private String disponibilidade;

    public String getDisponibilidade() {return disponibilidade;}

    public void setDisponibilidade(String tipo) {
        this.disponibilidade = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
