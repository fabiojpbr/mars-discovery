package sako.fabio.nasa.discovery.exceptions;
/**
 * Erro lançado quando ocorre a ultrapassagem de uma fronteira
 * @author fabio
 *
 */
public class BordersInvasionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5249934201682973167L;

	/**
	 * Construtor que recebe a mensagem de erro
	 * @param message utilizado para mostrar para o usuário o motivo do erro
	 */
	public BordersInvasionException(String message) {
		super(message);
	}

	
}
