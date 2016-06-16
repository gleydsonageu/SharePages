package br.com.projetoapp.sharepages.infra;


import br.com.projetoapp.sharepages.dominio.Usuario;

public class SharepagesException  extends  Exception{

    public SharepagesException (String mensagem){
        super(mensagem);
    }
    public SharepagesException (String mensagem, Throwable causa){
        super(mensagem, causa);
    }


}
