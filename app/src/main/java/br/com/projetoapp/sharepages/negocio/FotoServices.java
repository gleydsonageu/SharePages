package br.com.projetoapp.sharepages.negocio;


import br.com.projetoapp.sharepages.dominio.Foto;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.FotoDAO;

public class FotoServices {


    private static FotoServices instancia = new FotoServices();
    private FotoDAO fotoDAO;

    private FotoServices(){
        this.fotoDAO = FotoDAO.getInstancia();
    }

    public static FotoServices getInstancia() {
        FotoServices  instancia = new FotoServices();
        return instancia;
    }

    public Foto inserirFoto(Foto foto) throws SharepagesException{

        try {
            int  idFoto = (int) fotoDAO.inserirFoto(foto);
            foto.setId(idFoto);
        } catch (SharepagesException e) {
            throw new SharepagesException("Houve um erro inserir foto");
        }
        return foto;

    }
    public void alterarFoto(Foto alteracaoFoto) throws SharepagesException{
        fotoDAO.alterar(alteracaoFoto);
    }
}
