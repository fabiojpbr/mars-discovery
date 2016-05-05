package sako.fabio.nasa.discovery.manager;

import java.util.Collection;

import org.springframework.stereotype.Service;

import sako.fabio.nasa.discovery.enums.Command;
import sako.fabio.nasa.discovery.enums.Direction;
import sako.fabio.nasa.discovery.enums.Status;
import sako.fabio.nasa.discovery.exceptions.AlreadyCreatedException;
import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.exceptions.ObjectNasaNotFoundException;
import sako.fabio.nasa.discovery.manager.interfaces.DiscoveryManagerInterface;
import sako.fabio.nasa.discovery.model.CommandExecution;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Probe addProbe(Identify<String> name, Coordination coordination, Direction direction) {
		if (plateau == null) {
			throw new ObjectNasaNotFoundException("Plateau not exists");
		}
		if (plateau.getProbeByName(name) != null) {
			throw new AlreadyCreatedException("Probe already exists!");
		}
		Probe probe = new Probe(name, coordination, direction, this.plateau);

		plateau.alterCoordination(coordination, probe);
		return probe;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Probe executeCommand(Identify<String> name, Collection<Command> commands) {
		Probe probe = plateau.getProbeByName(name);
		if (probe == null) {
			throw new ObjectNasaNotFoundException(String.format("Probe: %s not found", name));
		}
		for (Command command : commands) {
			command.execute(probe);
		}
		return probe;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPlateau(Plateau plateau) {
		if (plateau == null || plateau.getHeight() < 1 || plateau.getWidth() < 1) {
			throw new IllegalArgumentException("Check the values, height and width need to be greater than 0");
		}
		if (this.plateau != null) {
			throw new AlreadyCreatedException("Plateau already exits");
		}
		this.plateau = plateau;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Probe> getProbes() {
		Collection<Probe> probes = this.plateau.getProbes();
		return probes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Probe findProbeByName(Identify<String> identify) {
		Probe probe = this.plateau.getProbeByName(identify);
		if (probe == null) {
			throw new ObjectNasaNotFoundException(String.format("Probe: %s not found", identify));
		}
		return probe;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Plateau getPlateau() {
		if (plateau == null) {
			throw new ObjectNasaNotFoundException("Plateau not found");
		}
		return plateau;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteProbeByName(Identify<String> identify) {
		plateau.deleteProbeByName(identify);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deletePlateau() {
		this.plateau = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<CommandExecution<Identify<String>, String>> executeCommand(Collection<CommandExecution<Identify<String>, String>> commandExecutions) {
		
		for(CommandExecution<Identify<String>, String> execution: commandExecutions){
			try{
				executeCommand(execution.getName(), execution.getCommands());
				execution.setStatusExecution(Status.OK, "Executado com sucesso");
			}catch(BusyPlaceException | BordersInvasionException e){
				execution.setStatusExecution(Status.ERROR, e.getMessage());
			}catch(ObjectNasaNotFoundException e){
				execution.setStatusExecution(Status.NOT_FOUND,e.getMessage());
			}
		}
		
		return commandExecutions;
	}

}
