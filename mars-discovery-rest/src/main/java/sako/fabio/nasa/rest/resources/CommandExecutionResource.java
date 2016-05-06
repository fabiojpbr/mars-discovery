package sako.fabio.nasa.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import sako.fabio.nasa.discovery.enums.Status;
import sako.fabio.nasa.discovery.model.Identify;

public class CommandExecutionResource extends ResourceSupport{
	private Identify<String> plateauName;
	private Identify<String> probeName;
	private Status status;
	private String message;
	public CommandExecutionResource(Identify<String> plateauName, Identify<String> probeName, Status status, String message) {
		super();
		this.plateauName = plateauName;
		this.probeName = probeName;
		this.status = status;
		this.message = message;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}

	public Identify<String> getProbeName() {
		return probeName;
	}

	public Identify<String> getPlateauName() {
		return plateauName;
	}
	
	

}
