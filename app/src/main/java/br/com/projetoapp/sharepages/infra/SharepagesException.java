package br.com.projetoapp.sharepages.infra;


public class SharepagesException  extends  Exception{

    public SharepagesException (String mensagem){
        super(mensagem);
    }
    public SharepagesException (String mensagem, Throwable causa){
        super(mensagem, causa);
    }

}
