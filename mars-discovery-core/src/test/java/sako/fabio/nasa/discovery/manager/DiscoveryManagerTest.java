package sako.fabio.nasa.discovery.manager;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import sako.fabio.nasa.discovery.enums.Direction;
import sako.fabio.nasa.discovery.exceptions.AlreadyCreatedException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.exceptions.InvalidCommandException;
import sako.fabio.nasa.discovery.exceptions.ObjectNasaNotFoundException;
import sako.fabio.nasa.discovery.manager.interfaces.DiscoveryManagerInterface;
import sako.fabio.nasa.discovery.model.Coordination;
import sako.fabio.nasa.discovery.model.Identify;
import sako.fabio.nasa.discovery.model.Plateau;
import sako.fabio.nasa.discovery.model.Probe;

public class DiscoveryManagerTest {
	
	private static final String PROBE_NAME = "probe_1";
	
	private DiscoveryManagerInterface discoveryManager;
	
	@Before
	public void setUp(){
		discoveryManager = new DiscoveryManager(new Plateau(5, 5));
	}
	
	@Test
	public void testAddProbe() {
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction cardinalPointInitial = Direction.N;
		Coordination coordination = new Coordination(x, y);
		Probe probe = discoveryManager.addProbe(id, coordination, cardinalPointInitial);
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
		discoveryManager.addProbe(id, coordination, cardinalPointInitial);
	}
	
	@Test(expected= AlreadyCreatedException.class)
	public void testAddProbeAlreadyCreated() {
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction cardinalPointInitial = Direction.N;
		Coordination coordination = new Coordination(x, y);

		discoveryManager.addProbe(id, coordination, cardinalPointInitial);
		discoveryManager.addProbe(id, coordination, cardinalPointInitial);
		
	}
	
	@Test
	public void testCommand(){
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction cardinalPointInitial = Direction.N;
		Coordination coordination = new Coordination(x, y);

		discoveryManager.addProbe(id, coordination, cardinalPointInitial);

		List<String> commands = Arrays.asList("L","M","L","M","L","M","L","M","M");
		Probe probe = discoveryManager.command(id, commands);
		int xExpected = 1;
		int yExpected = 3;
		Direction cardinalPointExpected = Direction.N;
		Assert.assertEquals(xExpected, probe.getCoordination().getX());
		Assert.assertEquals(yExpected, probe.getCoordination().getY());
		Assert.assertEquals(cardinalPointInitial, cardinalPointExpected);
	}
	
	@Test(expected=InvalidCommandException.class)
	public void testCommandInvalid(){
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction cardinalPointInitial = Direction.N;
		Coordination coordination = new Coordination(x, y);

		discoveryManager.addProbe(id, coordination, cardinalPointInitial);
		List<String> commands = Arrays.asList("L","M","L","M","L","x","L","M","M");
		discoveryManager.command(id, commands);
	}
	
	@Test(expected=BusyPlaceException.class)
	public void testCommandConflictsPlaces(){
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>(PROBE_NAME);
		Direction cardinalPointInitial = Direction.N;
		Coordination coordination = new Coordination(x, y);
		discoveryManager.addProbe(id, coordination, cardinalPointInitial);
		
		int x2 = 1;
		int y2 = 3;
		Identify<String> id2 = new Identify<String>("probe_2");
		Direction cardinalPointInitial2 = Direction.N;
		Coordination coordination2 = new Coordination(x2, y2);
		discoveryManager.addProbe(id2, coordination2, cardinalPointInitial2);
		
		List<String> commands = Arrays.asList("L","M","L","M","L","M","L","M","M");
		discoveryManager.command(id, commands);
	}
	
	@Test(expected=ObjectNasaNotFoundException.class)
	public void testCommandProbeNotFound() {
		Identify<String> id = new Identify<String>(PROBE_NAME);		
		List<String> commands = Arrays.asList("L","M","L","M","L","M","L","M","M");
		discoveryManager.command(id, commands);
	}
	
	@Test
	public void testSetPlateau(){
		discoveryManager = new DiscoveryManager();
		discoveryManager.setPlateau(new Plateau(5, 5));
	}
	
	@Test(expected = AlreadyCreatedException.class)
	public void testSetPlateauAlreadyExists(){
		discoveryManager = new DiscoveryManager();
		discoveryManager.setPlateau(new Plateau(5, 5));
		discoveryManager.setPlateau(new Plateau(5, 5));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetPlateauNull(){
		discoveryManager = new DiscoveryManager();
		discoveryManager.setPlateau(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetPlateauHeightLessThanOne(){
		discoveryManager = new DiscoveryManager();
		discoveryManager.setPlateau(new Plateau(0,1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetPlateauWidthLessThanOne(){
		discoveryManager = new DiscoveryManager();
		discoveryManager.setPlateau(new Plateau(1,0));
	}
	
	@Test
	public void testGetPlobes(){
		discoveryManager.addProbe(new Identify<String>(PROBE_NAME), new Coordination(0, 0), Direction.N);
		Collection<Probe> probes = discoveryManager.getProbes();
		Assert.assertFalse(probes.isEmpty());
	}
	@Test
	public void testFindProbeByName(){
		discoveryManager.addProbe(new Identify<String>(PROBE_NAME), new Coordination(0, 0), Direction.N);
		Probe probe = discoveryManager.findProbeByName(new Identify<String>(PROBE_NAME));
		Assert.assertNotNull(probe);
		Assert.assertEquals(PROBE_NAME, probe.getName().getId());
	}
	
	@Test(expected=ObjectNasaNotFoundException.class)
	public void testFindProbeByNameNotFound(){
		discoveryManager.findProbeByName(new Identify<String>(PROBE_NAME));
	}
	
	@Test
	public void testGetPlateau(){
		Plateau plateau = discoveryManager.getPlateau();
		Assert.assertNotNull(plateau);
	}
	
	@Test(expected=ObjectNasaNotFoundException.class)
	public void testGetPlateauByNameNotFound(){
		discoveryManager = new DiscoveryManager();
		discoveryManager.getPlateau();
	}
	
	@Test
	public void testDeleteProbeByName(){
		discoveryManager.addProbe(new Identify<String>(PROBE_NAME), new Coordination(0, 0), Direction.N);
		discoveryManager.deleteProbeByName(new Identify<String>(PROBE_NAME));
		Assert.assertTrue(discoveryManager.getProbes().isEmpty());
	}
	
	@Test(expected = ObjectNasaNotFoundException.class)
	public void testDeleteProbeByNameNotFound(){
		discoveryManager.deleteProbeByName(new Identify<String>(PROBE_NAME));
	}
	
	
}
