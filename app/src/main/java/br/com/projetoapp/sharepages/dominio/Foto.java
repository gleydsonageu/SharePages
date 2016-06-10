package br.com.projetoapp.sharepages.dominio;

public class Foto {
    private int id;
    private String caminho;
    private int idUnidadeLivro;

    public Foto(String caminho) {
        this.caminho = caminho;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public int getIdUnidadeLivro() {
        return idUnidadeLivro;
    }

    public void setIdUnidadeLivro(int idUnidadeLivro) {
        this.idUnidadeLivro = idUnidadeLivro;
    }
}
