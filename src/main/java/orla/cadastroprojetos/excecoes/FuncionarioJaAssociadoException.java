package orla.cadastroprojetos.excecoes;

public class FuncionarioJaAssociadoException extends RuntimeException {

    public FuncionarioJaAssociadoException(String message) {
        super(message);
    }
}