package sako.fabio.nasa.rest.model;

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
	private String message;
	/**
	 * Construtor
	 * @param url do recurso
	 * @param message de erro
	 */
	public ErrorInfo(String url, String message) {
		super();
		this.url = url;
		this.message = message;
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
	 * Obtem a mensagem de erro
	 * @return
	 */
	@JacksonXmlProperty(localName="message")
	public String getMessage() {
		return message;
	}
	
	
	
}
