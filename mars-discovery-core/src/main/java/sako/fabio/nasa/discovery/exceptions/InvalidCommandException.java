package sako.fabio.nasa.discovery.exceptions;
/**
 * Erro lançado quando informa um comando inválido
 * @author fabio
 *
 */
public class InvalidCommandException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4648174563480162065L;

	/**
	 * Construtor que recebe a mensagem de erro
	 * @param message utilizado para mostrar para o usuário o motivo do erro
	 */
	public InvalidCommandException(String message) {
		super(message);

	}

	
}
