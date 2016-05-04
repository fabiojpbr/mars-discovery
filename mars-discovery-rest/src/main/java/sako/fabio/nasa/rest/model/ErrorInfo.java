package sako.fabio.nasa.rest.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName="error-info")
public class ErrorInfo {
	private String url;
	private String message;
	
	public ErrorInfo(String url, String message) {
		super();
		this.url = url;
		this.message = message;
	}
	@JacksonXmlProperty(localName="url")
	public String getUrl() {
		return url;
	}
	@JacksonXmlProperty(localName="message")
	public String getMessage() {
		return message;
	}
	
	
	
}
