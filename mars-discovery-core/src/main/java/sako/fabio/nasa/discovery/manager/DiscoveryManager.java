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
import sako.fabio.nasa.discovery.validation.PlateauValidator;
import sako.fabio.nasa.discovery.validation.ProbeValidator;
/**
 * Implementação do Gerenciador da Nasa
 * Esta classe possui os principais métodos para realizar a Exploração a Marte
 * @author fabio
 *
 */
@Service
public class DiscoveryManager implements DiscoveryManagerInterface {
	private Plateau plateau;



	public DiscoveryManager() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Probe addProbe(Identify<String> plateauName, Identify<String> name, Coordination coordination, Direction direction) {
		checkPlateauExistis(plateauName);
		
		if (plateau.getProbeByName(name) != null) {
			throw new AlreadyCreatedException("Probe already exists!");
		}
		Probe probe = new Probe(name, coordination, direction, this.plateau);
		new ProbeValidator().check(probe);
		plateau.alterCoordination(coordination, probe);
		return probe;
	}
	
	private void checkPlateauExistis(Identify<String> plateauName){
		if (plateau == null || ! plateau.getName().equals(plateauName)) {
			throw new ObjectNasaNotFoundException(String.format("Plateau[%s] not exists", plateauName));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Probe executeCommand(Identify<String> plateauName, Identify<String> name, Collection<Command> commands) {
		checkPlateauExistis(plateauName);
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
		new PlateauValidator().check(plateau);
		if (this.plateau != null) {
			throw new AlreadyCreatedException("Plateau already exits");
		}
		this.plateau = plateau;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Probe> getProbesByPlateauName(Identify<String> plateauName) {
		checkPlateauExistis(plateauName);
		Collection<Probe> probes = this.plateau.getProbes();
		return probes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Probe findProbeByName(Identify<String> plateauName, Identify<String> identify) {
		checkPlateauExistis(plateauName);
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
	public Plateau findPlateauByName(Identify<String> plateauName) {
		checkPlateauExistis(plateauName);
		return plateau;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteProbeByName(Identify<String> plateauName, Identify<String> probeId) {
		checkPlateauExistis(plateauName);
		plateau.deleteProbeByName(probeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deletePlateau(Identify<String> plateauName) {
		this.plateau = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<CommandExecution> executeCommand(Identify<String> plateauName, Collection<CommandExecution> commandExecutions) {
		
		for(CommandExecution execution: commandExecutions){
			try{
				executeCommand(plateauName, execution.getProbeName(), execution.getCommands());
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
