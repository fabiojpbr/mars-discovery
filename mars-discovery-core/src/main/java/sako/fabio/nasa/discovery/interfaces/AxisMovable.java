package sako.fabio.nasa.discovery.interfaces;

import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;

public interface AxisMovable{
	void upX() throws BordersInvasionException, BusyPlaceException;
	void downX() throws BordersInvasionException, BusyPlaceException;
	void upY() throws BordersInvasionException, BusyPlaceException;
	void downY() throws BordersInvasionException, BusyPlaceException;
}
