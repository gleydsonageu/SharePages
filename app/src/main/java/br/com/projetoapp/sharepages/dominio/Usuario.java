package br.com.projetoapp.sharepages.dominio;


public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private Cidade cidade;
    private int idCidade;

    public Usuario(String nome, String email, String senha, int idCidade) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idCidade = idCidade;
    }
    public  Usuario(){
    }
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public int getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }
}

