package sako.fabio.nasa.discovery.manager;

import java.util.Collection;

import org.springframework.stereotype.Service;

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
import sako.fabio.nasa.discovery.exceptions.ObjectNasaNotFound;
import sako.fabio.nasa.discovery.manager.interfaces.DiscoveryManagerInterface;
@Service
public class DiscoveryManager implements DiscoveryManagerInterface {
	private Plateau plateau;

	public DiscoveryManager(Plateau plateau) {
		this.plateau = plateau;
	}
	
	public DiscoveryManager() {
		super();
	}

	public Probe addProbe(Identify<String> name, Coordination coordination, CardinalPoint cardinalPointInitial) throws BordersInvasionException, BusyPlaceException, AlreadyCreatedException {
		if (plateau.getProbeById(name) != null){
			throw new AlreadyCreatedException();
		}
		Probe probe = new Probe(name, coordination, cardinalPointInitial, this.plateau);
		
		plateau.alterCoordination(coordination, probe);
		return probe;
	}

	public Probe command(Identify<String> name, Collection<String> commands) throws BordersInvasionException, BusyPlaceException, InvalidCommandException, ObjectNasaNotFound {
		Probe probe = plateau.getProbeById(name);
		if(probe == null){
			throw new ObjectNasaNotFound();
		}
		for(String command: commands){
			try{
				Command.valueOf(command.toUpperCase()).execute(probe);
			}catch(IllegalArgumentException e){
				throw new InvalidCommandException();
			}
			
		}
		return probe;
	}

	public void setPlateau(Plateau plateau){
		if(this.plateau != null){
			throw new AlreadyCreatedException();
		}
		this.plateau = plateau;
	}
	
	@Override
	public Collection<Probe> getProbes() {
		Collection<Probe> probes = this.plateau.getProbes();
		if(probes == null || probes.isEmpty()){
			throw new ObjectNasaNotFound();
		}
		return probes; 
	}
	@Override
	public Probe findProbeByName(Identify<String> identify) {
		Probe probe = this.plateau.getProbeById(identify);
		if(probe == null){
			throw new ObjectNasaNotFound();
		}
		return probe;
	}
	

}
