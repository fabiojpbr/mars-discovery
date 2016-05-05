package sako.fabio.nasa.discovery.exceptions;
/**
 * Erro lançado quando o lugar já está ocupado
 * @author fabio
 *
 */
public class BusyPlaceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7013190502735971056L;

	/**
	 * Construtor que recebe a mensagem de erro
	 * @param message utilizado para mostrar para o usuário o motivo do erro
	 */
	public BusyPlaceException(String message) {
		super(message);
	}
	
	

}
