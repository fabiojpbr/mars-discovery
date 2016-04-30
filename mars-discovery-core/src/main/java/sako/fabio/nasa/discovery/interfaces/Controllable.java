package sako.fabio.nasa.discovery.interfaces;

import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;

public interface Controllable {
	
	public void turnRight();

	public void turnLeft();

	public void move() throws BordersInvasionException, BusyPlaceException;
}
