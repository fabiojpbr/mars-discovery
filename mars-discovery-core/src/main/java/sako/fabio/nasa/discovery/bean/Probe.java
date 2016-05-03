package sako.fabio.nasa.discovery.bean;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import sako.fabio.nasa.discovery.enums.Direction;
import sako.fabio.nasa.discovery.interfaces.AxisMovable;
import sako.fabio.nasa.discovery.interfaces.Controllable;
/**
 * Classe que representa a Sonda
 * @author fabio
 *
 */
@JacksonXmlRootElement(localName="probe")
public class Probe implements Serializable, AxisMovable,Controllable{
	
	private static final Logger LOGGER = Logger.getLogger(Probe.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -3507611299753181344L;
	private Identify<String> id;
	private Direction direction;
	private Coordination coordination;
	private Plateau plateau;
	
	@JsonCreator
	public Probe(
			@JsonProperty("id")
			Identify<String> id,
			@JsonProperty("coordination")
			Coordination coordination,
			@JsonProperty("cardinalPoint")
			
			Direction cardinalPoint) {
		super();
		this.direction = cardinalPoint;
		this.coordination = coordination;
		this.id = id;
	}
	
	public Probe(Identify<String> id, Coordination coordination, Direction cardinalPoint, Plateau plateau) {
		this(id,coordination,cardinalPoint);
		this.plateau = plateau;
	}
	
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName="cardinalPoint")
	public Direction getDirection() {
		return direction;
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName="coordination")
	public Coordination getCoordination() {
		return coordination;
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName="id")
	public Identify<String> getId() {
		return id;
	}
	
	public void turnRight(){
		this.direction = direction.getNextRight();
		LOGGER.info(String.format("Probe: %s CMD: Turn Right, New Point: %s", this.id,direction));
	}
	
	public void turnLeft(){
		this.direction = direction.getNextLeft();
		LOGGER.info(String.format("Probe: %s CMD: Turn Left, New Point: %s", this.id,direction));
	}
	
	public void move(){
		this.direction.move(this);
		LOGGER.info(String.format("Probe: %s CMD: Move, New Position: (%s)", this.id,coordination));

	}

	public void upX(){
		Coordination newCoordination = new Coordination(this.coordination.getX() + 1, this.coordination.getY());
		alterCoordination(newCoordination);
	}

	public void downX(){
		Coordination newCoordination = new Coordination(this.coordination.getX() - 1, this.coordination.getY());
		alterCoordination(newCoordination);
	}

	public void upY(){
		Coordination newCoordination = new Coordination(this.coordination.getX() , this.coordination.getY()+1);
		alterCoordination(newCoordination);
	}

	public void downY(){
		Coordination newCoordination = new Coordination(this.coordination.getX() , this.coordination.getY()-1);
		alterCoordination(newCoordination);
	}
	
	private void alterCoordination(Coordination newCoordination){
		this.plateau.alterCoordination(newCoordination, this);
		this.coordination = newCoordination;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Probe other = (Probe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
