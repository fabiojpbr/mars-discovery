package sako.fabio.nasa.discovery.manager.interfaces;

import java.rmi.AlreadyBoundException;
import java.util.Collection;

import sako.fabio.nasa.discovery.bean.Identify;
import sako.fabio.nasa.discovery.bean.Probe;
import sako.fabio.nasa.discovery.enums.CardinalPoint;
import sako.fabio.nasa.discovery.exceptions.AlreadyCreatedException;
import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.exceptions.InvalidCommandException;
/**
 * 
 * @author fabio
 *
 *Interface com os comandos do gerenciador da Discovery
 */
public interface DiscoveryManagerInterface {
	
	public Probe addProbe(Identify<String> name, int posXInitial, int posYInitial, CardinalPoint cardinalPointInitial) throws BordersInvasionException, BusyPlaceException, AlreadyBoundException, AlreadyCreatedException;
	
	public Probe command(Identify<String> name, Collection<String> commands) throws BordersInvasionException, BusyPlaceException, InvalidCommandException;

}
