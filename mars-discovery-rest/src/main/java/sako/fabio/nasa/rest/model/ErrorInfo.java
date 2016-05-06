package sako.fabio.nasa.rest.model;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
/**
 * Classe que contem a informação do erro
 * @author fabio
 *
 */
@JacksonXmlRootElement(localName="error-info")
public class ErrorInfo {
	private String url;
	private Collection<String> messages;
	/**
	 * Construtor
	 * @param url do recurso
	 * @param message de erro
	 */
	public ErrorInfo(String url) {
		super();
		this.url = url;
		this.messages = new ArrayList<>();
	}
	/**
	 * Obtem a url do recurso
	 * @return
	 */
	@JacksonXmlProperty(localName="url")
	public String getUrl() {
		return url;
	}
	/**
	 * Adiciona a mensagem de erro
	 * @param message
	 */
	public void addMessage(String message){
		this.messages.add(message);
	}
	/**
	 * Adiciona uma lista de mensagem de erros
	 * @param messages
	 */
	public void addAllMessage(Collection<String> messages){
		this.messages.addAll(messages);
	}
	/**
	 * Obtem a mensagem de erro
	 * @return
	 */
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName="messages")
	public Collection<String> getMessages() {
		Collection<String> messages = new ArrayList<>();
		messages.addAll(this.messages);
		return messages;
	}
	
	
	
}
