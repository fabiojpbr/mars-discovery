package sako.fabio.nasa.discovery.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import sako.fabio.nasa.discovery.enums.Command;
import sako.fabio.nasa.discovery.enums.Status;

public class CommandExecution<I extends Identify<T>, T> {
	private I id;
	private Status status;
	private String message;
	private Collection<Command> commands;
	
	@JsonCreator
	public CommandExecution(@JsonProperty("id") I id, @JsonProperty("commands") Collection<Command> commands) {
		super();
		this.id = id;
		this.commands = commands;
	}

	public I getId() {
		return id;
	}

	public Collection<Command> getCommands() {
		return commands;
	}

	public Status getStatus() {
		return status;
	}


	public String getMessage() {
		return message;
	}
	
	public void setStatusExecution(Status status, String message){
		this.status = status;
		this.message = message;
	}
	
}
