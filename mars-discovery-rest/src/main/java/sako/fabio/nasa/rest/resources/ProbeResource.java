package sako.fabio.nasa.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import sako.fabio.nasa.discovery.bean.Coordination;
import sako.fabio.nasa.discovery.enums.Direction;

public class ProbeResource extends ResourceSupport{
	private String name;
	private Coordination coordination;
	private Direction direction;
	
	public ProbeResource(String name, Coordination coordination, Direction direction) {
		super();
		this.name = name;
		this.coordination = coordination;
		this.direction = direction;
	}
	public Coordination getCoordination() {
		return coordination;
	}
	public Direction getDirection() {
		return direction;
	}
	public String getName() {
		return name;
	}
	
}