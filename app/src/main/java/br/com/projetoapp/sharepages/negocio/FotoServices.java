package br.com.projetoapp.sharepages.negocio;


import br.com.projetoapp.sharepages.dominio.Foto;
import br.com.projetoapp.sharepages.infra.SharepagesException;
import br.com.projetoapp.sharepages.persistencia.FotoDAO;

/**
 * FotoServices trata solicitações na FotoDAO e ações relacionadas a foto.
 */
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

    /**
     * Método que solicita a inserção de uma foto no banco de dados
     * @param foto
     * @return uma foto
     * @throws SharepagesException
     */
    public Foto inserirFoto(Foto foto) throws SharepagesException{

        try {
            int  idFoto = (int) fotoDAO.inserirFoto(foto);
            foto.setId(idFoto);
        } catch (SharepagesException e) {
            throw new SharepagesException("Houve um erro inserir foto");
        }
        return foto;

    }

    /**
     * Método que solicita a alteração da foto de uma unidadeLivro no banco de dados
     * @param alteracaoFoto
     * @throws SharepagesException
     */
    public void alterarFoto(Foto alteracaoFoto) throws SharepagesException{
        fotoDAO.alterar(alteracaoFoto);
    }
}
