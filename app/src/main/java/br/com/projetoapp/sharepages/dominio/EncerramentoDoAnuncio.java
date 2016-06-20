package br.com.projetoapp.sharepages.dominio;

public enum EncerramentoDoAnuncio {
    TRANSACAOEFETUADA("transacaoEfetuada"), DESISTENCIA("desistencia");

    private String nome;

    EncerramentoDoAnuncio(String nome) {
        this.nome = nome;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
