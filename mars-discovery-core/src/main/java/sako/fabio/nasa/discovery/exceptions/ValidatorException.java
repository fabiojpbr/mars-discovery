package sako.fabio.nasa.discovery.exceptions;

import java.util.Collection;
import java.util.HashSet;
/**
 * Erro lançado quando ocorre erro de validação
 * @author fabio
 *
 */
public class ValidatorException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5857030031158197520L;
	
	private Collection<String> messageErrors;

	public ValidatorException() {
		super();
		messageErrors = new HashSet<>();
	}
	/**
	 * Adiciona mensagem de erro
	 * @param message
	 */
	public void addMessage(String message){
		this.messageErrors.add(message);
	}
	/**
	 * Obtem a mensagem de erro
	 * @return
	 */
	public Collection<String> getMessages(){
		Collection<String> messages = new HashSet<>();
		messages.addAll(messageErrors);
		return messages;
	}
	

}
