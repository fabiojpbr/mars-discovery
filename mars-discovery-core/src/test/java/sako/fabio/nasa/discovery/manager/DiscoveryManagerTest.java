package sako.fabio.nasa.discovery.manager;

import static sako.fabio.nasa.discovery.enums.Command.L;
import static sako.fabio.nasa.discovery.enums.Command.M;
import static sako.fabio.nasa.discovery.enums.Command.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import sako.fabio.nasa.discovery.enums.Command;
import sako.fabio.nasa.discovery.enums.Direction;
import sako.fabio.nasa.discovery.enums.Status;
import sako.fabio.nasa.discovery.exceptions.AlreadyCreatedException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.exceptions.ObjectNasaNotFoundException;
import sako.fabio.nasa.discovery.manager.interfaces.DiscoveryManagerInterface;
import sako.fabio.nasa.discovery.model.CommandExecution;
import sako.fabio.nasa.discovery.model.Coordination;
import sako.fabio.nasa.discovery.model.Identify;
import sako.fabio.nasa.discovery.model.Plateau;
import sako.fabio.nasa.discovery.model.Probe;
/**
 * Testes unitários da Classe DiscoveryManager
 * @author fabio
 *
 */
public class DiscoveryManagerTest {
	
	private static final Identify<String> PLATEAU_NAME = new Identify<String>("OlympusMons");
	private static final String PROBE_NAME = "probe_1";

	private static final String PROBE_NAME_2 = "probe_2";
	
	
	
	private DiscoveryManagerInterface discoveryManager;
	
	@Before
	public void setUp(){
		Plateau plateau = new Plateau(PLATEAU_NAME, 5, 5);
		discoveryManager = new DiscoveryManager();
		discoveryManager.setPlateau(plateau);
	}
	
	@Test
	public void testAddProbe() {
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction cardinalPointInitial = Direction.N;
		Coordination coordination = new Coordination(x, y);
		Probe probe = discoveryManager.addProbe(PLATEAU_NAME, id, coordination, cardinalPointInitial);
		Assert.assertNotNull(probe);
		Assert.assertEquals(x, probe.getCoordination().getX());
		Assert.assertEquals(y, probe.getCoordination().getY());
	}
	
	@Test(expected = ObjectNasaNotFoundException.class)
	public void testAddProbePlateauNotExists(){
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction cardinalPointInitial = Direction.N;
		Coordination coordination = new Coordination(x, y);
		discoveryManager = new DiscoveryManager();
		discoveryManager.addProbe(PLATEAU_NAME, id, coordination, cardinalPointInitial);
	}
	
	@Test(expected= AlreadyCreatedException.class)
	public void testAddProbeAlreadyCreated() {
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction cardinalPointInitial = Direction.N;
		Coordination coordination = new Coordination(x, y);

		discoveryManager.addProbe(PLATEAU_NAME, id, coordination, cardinalPointInitial);
		discoveryManager.addProbe(PLATEAU_NAME, id, coordination, cardinalPointInitial);
		
	}
	
	@Test
	public void testCommandExecution(){
		Coordination coordination = new Coordination(1, 2);
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction directionInitial = Direction.N;
		discoveryManager.addProbe(PLATEAU_NAME, id, coordination, directionInitial);
		
		Coordination coordination2 = new Coordination(3, 3);
		Identify<String> id2 = new Identify<String>(PROBE_NAME_2);
		Direction directionInitial2 = Direction.E;
		discoveryManager.addProbe(PLATEAU_NAME, id2, coordination2, directionInitial2);
		Collection<CommandExecution> commands = new ArrayList<>();
		commands.add(new CommandExecution(id, Arrays.asList(L,M,L,M,L,M,L,M,M)));
		commands.add(new CommandExecution(id2, Arrays.asList(M,M,R,M,M,R,M,R,R,M)));
		Collection<CommandExecution> result = discoveryManager.executeCommand(PLATEAU_NAME, commands);
		Coordination coordinationExpected1 = new Coordination(1, 3);
		Direction directionExpected1 = Direction.N;
		Coordination coordinationExpected2 = new Coordination(5, 1);
		Direction directionExpected2 = Direction.E;
		
		for(CommandExecution commandResult: result){
			Probe p = discoveryManager.findProbeByName(PLATEAU_NAME, commandResult.getProbeName());
			if(commandResult.getProbeName().equals(id)){
				Assert.assertEquals(Status.OK, commandResult.getStatus());
				
				Assert.assertEquals(coordinationExpected1, p.getCoordination());
				Assert.assertEquals(directionExpected1, p.getDirection());
			}else if(commandResult.getProbeName().equals(id2)){
				Assert.assertEquals(Status.OK, commandResult.getStatus());
				Assert.assertEquals(coordinationExpected2, p.getCoordination());
				Assert.assertEquals(directionExpected2, p.getDirection());
			}else{
				Assert.fail();
			}
		}
	}
	
	@Test
	public void testCommandExecutionBusyPlace(){
		Coordination coordination = new Coordination(0, 0);
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction directionInitial = Direction.N;
		discoveryManager.addProbe(PLATEAU_NAME, id, coordination, directionInitial);
		
		Coordination coordination2 = new Coordination(0, 1);
		Identify<String> id2 = new Identify<String>(PROBE_NAME_2);
		Direction directionInitial2 = Direction.E;
		discoveryManager.addProbe(PLATEAU_NAME, id2, coordination2, directionInitial2);
		Collection<CommandExecution> commands = new ArrayList<>();
		commands.add(new CommandExecution(id, Arrays.asList(M)));
		commands.add(new CommandExecution(id2, Arrays.asList(M)));
		Collection<CommandExecution> result = discoveryManager.executeCommand(PLATEAU_NAME, commands);
		Coordination coordinationExpected1 = new Coordination(0, 0);
		Direction directionExpected1 = Direction.N;
		Coordination coordinationExpected2 = new Coordination(1, 1);
		Direction directionExpected2 = Direction.E;
		
		for(CommandExecution commandResult: result){
			Probe p = discoveryManager.findProbeByName(PLATEAU_NAME, commandResult.getProbeName());
			if(commandResult.getProbeName().equals(id)){
				Assert.assertEquals(Status.ERROR, commandResult.getStatus());
				
				Assert.assertEquals(coordinationExpected1, p.getCoordination());
				Assert.assertEquals(directionExpected1, p.getDirection());
			}else if(commandResult.getProbeName().equals(id2)){
				Assert.assertEquals(Status.OK, commandResult.getStatus());
				Assert.assertEquals(coordinationExpected2, p.getCoordination());
				Assert.assertEquals(directionExpected2, p.getDirection());
			}else{
				Assert.fail();
			}
		}
	}
	
	@Test
	public void testCommandExecutionBorderInvasion(){
		Coordination coordination = new Coordination(0, 0);
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction directionInitial = Direction.S;
		discoveryManager.addProbe(PLATEAU_NAME, id, coordination, directionInitial);
		
		Coordination coordination2 = new Coordination(0, 1);
		Identify<String> id2 = new Identify<String>(PROBE_NAME_2);
		Direction directionInitial2 = Direction.E;
		discoveryManager.addProbe(PLATEAU_NAME, id2, coordination2, directionInitial2);
		Collection<CommandExecution> commands = new ArrayList<>();
		commands.add(new CommandExecution(id, Arrays.asList(M)));
		commands.add(new CommandExecution(id2, Arrays.asList(M)));
		Collection<CommandExecution> result = discoveryManager.executeCommand(PLATEAU_NAME, commands);
		Coordination coordinationExpected1 = new Coordination(0, 0);
		Direction directionExpected1 = Direction.S;
		Coordination coordinationExpected2 = new Coordination(1, 1);
		Direction directionExpected2 = Direction.E;
		
		for(CommandExecution commandResult: result){
			Probe p = discoveryManager.findProbeByName(PLATEAU_NAME, commandResult.getProbeName());
			if(commandResult.getProbeName().equals(id)){
				Assert.assertEquals(Status.ERROR, commandResult.getStatus());
				
				Assert.assertEquals(coordinationExpected1, p.getCoordination());
				Assert.assertEquals(directionExpected1, p.getDirection());
			}else if(commandResult.getProbeName().equals(id2)){
				Assert.assertEquals(Status.OK, commandResult.getStatus());
				Assert.assertEquals(coordinationExpected2, p.getCoordination());
				Assert.assertEquals(directionExpected2, p.getDirection());
			}else{
				Assert.fail();
			}
		}
	}
	
	@Test
	public void testCommandExecutionNotFound(){
		Coordination coordination = new Coordination(0, 0);
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction directionInitial = Direction.N;
		discoveryManager.addProbe(PLATEAU_NAME, id, coordination, directionInitial);
		
		Identify<String> id2 = new Identify<String>(PROBE_NAME_2);

		Collection<CommandExecution> commands = new ArrayList<>();
		commands.add(new CommandExecution(id, Arrays.asList(M)));
		commands.add(new CommandExecution(id2, Arrays.asList(M)));
		Collection<CommandExecution> result = discoveryManager.executeCommand(PLATEAU_NAME, commands);
		Coordination coordinationExpected1 = new Coordination(0, 1);
		Direction directionExpected1 = Direction.N;

		
		for(CommandExecution commandResult: result){
			
			if(commandResult.getProbeName().equals(id)){
				Probe p = discoveryManager.findProbeByName(PLATEAU_NAME, commandResult.getProbeName());
				Assert.assertEquals(Status.OK, commandResult.getStatus());
				
				Assert.assertEquals(coordinationExpected1, p.getCoordination());
				Assert.assertEquals(directionExpected1, p.getDirection());
			}else if(commandResult.getProbeName().equals(id2)){
				Assert.assertEquals(Status.NOT_FOUND, commandResult.getStatus());

			}else{
				Assert.fail();
			}
		}
	}
	
	@Test
	public void testCommand(){
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction cardinalPointInitial = Direction.N;
		Coordination coordination = new Coordination(x, y);

		discoveryManager.addProbe(PLATEAU_NAME, id, coordination, cardinalPointInitial);

		List<Command> commands = Arrays.asList(L,M,L,M,L,M,L,M,M);
		Probe probe = discoveryManager.executeCommand(PLATEAU_NAME, id, commands);
		int xExpected = 1;
		int yExpected = 3;
		Direction cardinalPointExpected = Direction.N;
		Assert.assertEquals(xExpected, probe.getCoordination().getX());
		Assert.assertEquals(yExpected, probe.getCoordination().getY());
		Assert.assertEquals(cardinalPointInitial, cardinalPointExpected);
	}
	
	@Test(expected=BusyPlaceException.class)
	public void testCommandConflictsPlaces(){
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction cardinalPointInitial = Direction.N;
		Coordination coordination = new Coordination(x, y);
		discoveryManager.addProbe(PLATEAU_NAME, id, coordination, cardinalPointInitial);
		
		int x2 = 1;
		int y2 = 3;
		Identify<String> id2 = new Identify<String>("probe_2");
		Direction cardinalPointInitial2 = Direction.N;
		Coordination coordination2 = new Coordination(x2, y2);
		discoveryManager.addProbe(PLATEAU_NAME, id2, coordination2, cardinalPointInitial2);
		
		List<Command> commands = Arrays.asList(L,M,L,M,L,M,L,M,M);
		discoveryManager.executeCommand(PLATEAU_NAME, id, commands);
	}
	
	@Test(expected=ObjectNasaNotFoundException.class)
	public void testCommandProbeNotFound() {
		Identify<String> id = new Identify<String>(PROBE_NAME);		
		List<Command> commands = Arrays.asList(L,M,L,M,L,M,L,M,M);
		discoveryManager.executeCommand(PLATEAU_NAME, id, commands);
	}
	
	@Test
	public void testSetPlateau(){
		discoveryManager = new DiscoveryManager();
		discoveryManager.setPlateau(new Plateau(PLATEAU_NAME, 5, 5));
	}
	
	@Test(expected = AlreadyCreatedException.class)
	public void testSetPlateauAlreadyExists(){
		discoveryManager = new DiscoveryManager();
		discoveryManager.setPlateau(new Plateau(PLATEAU_NAME, 5, 5));
		discoveryManager.setPlateau(new Plateau(PLATEAU_NAME, 5, 5));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetPlateauNull(){
		discoveryManager = new DiscoveryManager();
		discoveryManager.setPlateau(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetPlateauHeightLessThanOne(){
		discoveryManager = new DiscoveryManager();
		discoveryManager.setPlateau(new Plateau(PLATEAU_NAME,0, 1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetPlateauWidthLessThanOne(){
		discoveryManager = new DiscoveryManager();
		discoveryManager.setPlateau(new Plateau(PLATEAU_NAME,1, 0));
	}
	
	@Test
	public void testGetPlobes(){
		discoveryManager.addProbe(PLATEAU_NAME, new Identify<String>(PROBE_NAME), new Coordination(0, 0), Direction.N);
		Collection<Probe> probes = discoveryManager.getProbesByPlateauName(PLATEAU_NAME);
		Assert.assertFalse(probes.isEmpty());
	}
	@Test
	public void testFindProbeByName(){
		discoveryManager.addProbe(PLATEAU_NAME, new Identify<String>(PROBE_NAME), new Coordination(0, 0), Direction.N);
		Probe probe = discoveryManager.findProbeByName(PLATEAU_NAME, new Identify<String>(PROBE_NAME));
		Assert.assertNotNull(probe);
		Assert.assertEquals(PROBE_NAME, probe.getName().getId());
	}
	
	@Test(expected=ObjectNasaNotFoundException.class)
	public void testFindProbeByNameNotFound(){
		discoveryManager.findProbeByName(PLATEAU_NAME, new Identify<String>(PROBE_NAME));
	}
	
	@Test
	public void testGetPlateau(){
		Plateau plateau = discoveryManager.findPlateauByName(PLATEAU_NAME);
		Assert.assertNotNull(plateau);
	}
	
	@Test(expected=ObjectNasaNotFoundException.class)
	public void testGetPlateauByNameNotFound(){
		discoveryManager = new DiscoveryManager();
		discoveryManager.findPlateauByName(PLATEAU_NAME);
	}
	
	@Test
	public void testDeleteProbeByName(){
		discoveryManager.addProbe(PLATEAU_NAME, new Identify<String>(PROBE_NAME), new Coordination(0, 0), Direction.N);
		discoveryManager.deleteProbeByName(PLATEAU_NAME, new Identify<String>(PROBE_NAME));
		Assert.assertTrue(discoveryManager.getProbesByPlateauName(PLATEAU_NAME).isEmpty());
	}
	
	@Test(expected = ObjectNasaNotFoundException.class)
	public void testDeleteProbeByNameNotFound(){
		discoveryManager.deleteProbeByName(PLATEAU_NAME, new Identify<String>(PROBE_NAME));
	}
	
	@Test(expected = ObjectNasaNotFoundException.class)
	public void deletePlateau(){
		discoveryManager.deletePlateau(PLATEAU_NAME);
		discoveryManager.findPlateauByName(PLATEAU_NAME);
	}
	
	
}
