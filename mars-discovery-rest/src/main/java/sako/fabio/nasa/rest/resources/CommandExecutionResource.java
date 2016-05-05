package sako.fabio.nasa.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import sako.fabio.nasa.discovery.enums.Status;
import sako.fabio.nasa.discovery.model.Identify;

public class CommandExecutionResource extends ResourceSupport{
	private Identify<String> identify;
	private Status status;
	private String message;
	public CommandExecutionResource(Identify<String> identify, Status status, String message) {
		super();
		this.identify = identify;
		this.status = status;
		this.message = message;
	}
	
	public Identify<String> getIdentify() {
		return identify;
	}

	public Status getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	

}
