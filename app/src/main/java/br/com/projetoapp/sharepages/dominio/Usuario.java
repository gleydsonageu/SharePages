package br.com.projetoapp.sharepages.dominio;

/**
 * Created by gleydson on 17/05/16.
 */
public class Usuario {
    private String nome;
    private String email;
    private String senha;

    @Override
    public String toString(){
        return nome;
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

}
