package sako.fabio.nasa.discovery.bean;

import java.io.Serializable;

import org.apache.log4j.Logger;

import sako.fabio.nasa.discovery.enums.CardinalPoint;
import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.interfaces.AxisMovable;
import sako.fabio.nasa.discovery.interfaces.Controllable;

public class Probe implements Serializable, AxisMovable,Controllable{
	
	private static final Logger LOGGER = Logger.getLogger(Probe.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -3507611299753181344L;
	private Identify<String> id;
	private CardinalPoint cardinalPoint;
	private Coordination coordination;
	private Plateau plateau;
	
	public Probe(Identify<String> id, int posX, int posY, CardinalPoint cardinalPoint, Plateau plateau) {
		super();
		this.cardinalPoint = cardinalPoint;
		this.plateau = plateau;
		this.coordination = new Coordination(posX, posY);
		this.id = id;
	}
	
	
	public CardinalPoint getCardinalPoint() {
		return cardinalPoint;
	}

	public Coordination getCoordination() {
		return coordination;
	}
	
	public Identify<String> getId() {
		return id;
	}
	
	public void turnRight(){
		this.cardinalPoint = cardinalPoint.getNextRight();
		LOGGER.info(String.format("Probe: %s CMD: Turn Right, New Point: %s", this.id,cardinalPoint));
	}
	
	public void turnLeft(){
		this.cardinalPoint = cardinalPoint.getNextLeft();
		LOGGER.info(String.format("Probe: %s CMD: Turn Left, New Point: %s", this.id,cardinalPoint));
	}
	
	public void move() throws BordersInvasionException, BusyPlaceException{
		this.cardinalPoint.move(this);
		LOGGER.info(String.format("Probe: %s CMD: Move, New Position: (%s)", this.id,coordination));

	}

	public void upX() throws BordersInvasionException, BusyPlaceException{
		Coordination newCoordination = new Coordination(this.coordination.getX() + 1, this.coordination.getY());
		alterCoordination(newCoordination);
	}

	public void downX()  throws BordersInvasionException, BusyPlaceException{
		Coordination newCoordination = new Coordination(this.coordination.getX() - 1, this.coordination.getY());
		alterCoordination(newCoordination);
	}

	public void upY() throws BordersInvasionException, BusyPlaceException {
		Coordination newCoordination = new Coordination(this.coordination.getX() , this.coordination.getY()+1);
		alterCoordination(newCoordination);
	}

	public void downY()  throws BordersInvasionException, BusyPlaceException{
		Coordination newCoordination = new Coordination(this.coordination.getX() , this.coordination.getY()-1);
		alterCoordination(newCoordination);
	}
	
	private void alterCoordination(Coordination newCoordination) throws BordersInvasionException, BusyPlaceException{
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
