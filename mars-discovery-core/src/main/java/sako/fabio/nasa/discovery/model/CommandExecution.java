package sako.fabio.nasa.discovery.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import sako.fabio.nasa.discovery.enums.Command;
import sako.fabio.nasa.discovery.enums.Status;
/**
 * Classe que representa um conjunto de Commandos que serão executados
 * @author fabio
 *
 */
public class CommandExecution {
	private Identify<String> probeName;
	private Identify<String> plateuName;
	private Status status;
	private String message;
	private Collection<Command> commands;
		
	@JsonCreator
	public CommandExecution(@JsonProperty("probeName") Identify<String> probeName, @JsonProperty("commands") Collection<Command> commands) {
		super();
		this.probeName = probeName;
		this.commands = commands;
	}
	@JsonIgnore
	public void setPlateuName(Identify<String> plateuName) {
		this.plateuName = plateuName;
	}

	public Identify<String> getProbeName() {
		return probeName;
	}
	@JsonIgnore
	public Identify<String> getPlateuName() {
		return plateuName;
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
