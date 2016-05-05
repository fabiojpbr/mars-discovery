package sako.fabio.nasa.discovery.exceptions;

/**
 * Erro lançado quando o objeto da Nasa não foi encontrado
 * @author fabio
 *
 */
public class ObjectNasaNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7512992491810290288L;

	/**
	 * Construtor que recebe a mensagem de erro
	 * @param message utilizado para mostrar para o usuário o motivo do erro
	 */
	public ObjectNasaNotFoundException(String message) {
		super(message);
	}
	
}
