package sako.fabio.nasa.discovery.manager;

import java.rmi.AlreadyBoundException;
import java.util.Collection;

import sako.fabio.nasa.discovery.bean.Coordination;
import sako.fabio.nasa.discovery.bean.Identify;
import sako.fabio.nasa.discovery.bean.Plateau;
import sako.fabio.nasa.discovery.bean.Probe;
import sako.fabio.nasa.discovery.enums.CardinalPoint;
import sako.fabio.nasa.discovery.enums.Command;
import sako.fabio.nasa.discovery.exceptions.AlreadyCreatedException;
import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.exceptions.InvalidCommandException;
import sako.fabio.nasa.discovery.manager.interfaces.DiscoveryManagerInterface;

public class DiscoveryManager implements DiscoveryManagerInterface {
	private Plateau plateau;

	public DiscoveryManager(Plateau plateau) {
		super();
		this.plateau = plateau;
	}

	public Probe addProbe(Identify<String> name, int posXInitial, int posYInitial, CardinalPoint cardinalPointInitial) throws BordersInvasionException, BusyPlaceException, AlreadyCreatedException {
		if (plateau.getProbeById(name) != null){
			throw new AlreadyCreatedException();
		}
		Probe probe = new Probe(name, posXInitial, posYInitial, cardinalPointInitial, this.plateau);
		
		plateau.alterCoordination(new Coordination(posXInitial, posYInitial), probe);
		return probe;
	}

	public Probe command(Identify<String> name, Collection<String> commands) throws BordersInvasionException, BusyPlaceException, InvalidCommandException {
		
		Probe probe = plateau.getProbeById(name);
		for(String command: commands){
			try{
				Command.valueOf(command).execute(probe);
			}catch(IllegalArgumentException e){
				throw new InvalidCommandException();
			}
			
		}
		return probe;
	}

}
