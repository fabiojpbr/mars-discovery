package sako.fabio.nasa.discovery.manager.interfaces;

import java.rmi.AlreadyBoundException;
import java.util.Collection;

import sako.fabio.nasa.discovery.bean.Coordination;
import sako.fabio.nasa.discovery.bean.Identify;
import sako.fabio.nasa.discovery.bean.Plateau;
import sako.fabio.nasa.discovery.bean.Probe;
import sako.fabio.nasa.discovery.enums.Direction;
import sako.fabio.nasa.discovery.exceptions.AlreadyCreatedException;
import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
/**
 * 
 * @author fabio
 *
 *Interface com os comandos do gerenciador da Discovery
 */
public interface DiscoveryManagerInterface {
	/**
	 * Adiciona um Sonar no Planalto
	 * @param name
	 * @param posXInitial
	 * @param posYInitial
	 * @param cardinalPointInitial
	 * @return
	 * @throws BordersInvasionException
	 * @throws BusyPlaceException
	 * @throws AlreadyBoundException
	 * @throws AlreadyCreatedException
	 */
	public Probe addProbe(Identify<String> name, Coordination coordination, Direction cardinalPointInitial);
	
	public Probe command(Identify<String> name, Collection<String> commands);

	public void setPlateau(Plateau plateau);
	
	public Collection<Probe> getProbes();
	
	public Probe findProbeByName(Identify<String> identify);
}
