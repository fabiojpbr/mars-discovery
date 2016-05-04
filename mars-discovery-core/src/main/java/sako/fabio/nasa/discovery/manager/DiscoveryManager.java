package sako.fabio.nasa.discovery.manager;

import java.util.Collection;

import org.springframework.stereotype.Service;

import sako.fabio.nasa.discovery.enums.Command;
import sako.fabio.nasa.discovery.enums.Direction;
import sako.fabio.nasa.discovery.exceptions.AlreadyCreatedException;
import sako.fabio.nasa.discovery.exceptions.InvalidCommandException;
import sako.fabio.nasa.discovery.exceptions.ObjectNasaNotFoundException;
import sako.fabio.nasa.discovery.manager.interfaces.DiscoveryManagerInterface;
import sako.fabio.nasa.discovery.model.Coordination;
import sako.fabio.nasa.discovery.model.Identify;
import sako.fabio.nasa.discovery.model.Plateau;
import sako.fabio.nasa.discovery.model.Probe;
@Service
public class DiscoveryManager implements DiscoveryManagerInterface {
	private Plateau plateau;

	public DiscoveryManager(Plateau plateau) {
		this.plateau = plateau;
	}
	
	public DiscoveryManager() {
		super();
	}

	public Probe addProbe(Identify<String> name, Coordination coordination, Direction direction) {
		if(plateau == null){
			throw new ObjectNasaNotFoundException("Plateau not exists");
		}
		if (plateau.getProbeByName(name) != null){
			throw new AlreadyCreatedException("Probe already exists!");
		}
		Probe probe = new Probe(name, coordination, direction, this.plateau);
		
		plateau.alterCoordination(coordination, probe);
		return probe;
	}

	public Probe command(Identify<String> name, Collection<String> commands) {
		Probe probe = plateau.getProbeByName(name);
		if(probe == null){
			throw new ObjectNasaNotFoundException(String.format("Probe: %s not found", name));
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
		if(plateau == null || plateau.getHeight() < 1 || plateau.getWidth() < 1){
			throw new IllegalArgumentException("Check the values, height and width need to be greater than 0");
		}
		if(this.plateau != null){
			throw new AlreadyCreatedException("Plateau already exits");
		}
		this.plateau = plateau;
	}
	
	@Override
	public Collection<Probe> getProbes() {
		Collection<Probe> probes = this.plateau.getProbes();
		return probes; 
	}
	@Override
	public Probe findProbeByName(Identify<String> identify) {
		Probe probe = this.plateau.getProbeByName(identify);
		if(probe == null){
			throw new ObjectNasaNotFoundException(String.format("Probe: %s not found", identify));
		}
		return probe;
	}
	
	@Override
	public Plateau getPlateau() {
		if(plateau == null){
			throw new ObjectNasaNotFoundException("Plateau not found");
		}
		return plateau;
	}
	
	@Override
	public void deleteProbeByName(Identify<String> identify) {
		plateau.deleteProbeByName(identify);
	}
	
	@Override
	public void deletePlateau() {
		this.plateau = null;
	}

}
