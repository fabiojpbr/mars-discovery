package sako.fabio.nasa.discovery.exceptions;
/**
 * Indica que o objeto foi criado
 * @author fabio
 *
 */
public class AlreadyCreatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7013190502735971056L;

	/**
	 * Construtor que recebe a mensagem de erro
	 * @param message utilizado para mostrar para o usuário o motivo do erro
	 */
	public AlreadyCreatedException(String message) {
		super(message);
	}

	
}
