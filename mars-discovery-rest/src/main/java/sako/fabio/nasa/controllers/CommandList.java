package sako.fabio.nasa.controllers;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
@JacksonXmlRootElement(localName="command")
public class CommandList{

	private Collection commands;
	
	
	@JsonCreator()
	public CommandList(
			@JsonProperty("commands")
			Collection<String> commands) {
		super();
		this.commands = commands;
	}


	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName="commands")
	public Collection<String> getCommands() {
		return commands;
	}
	
	
}
