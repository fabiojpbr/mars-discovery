package sako.fabio.nasa.discovery.manager;

import java.rmi.AlreadyBoundException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import sako.fabio.nasa.discovery.bean.Coordination;
import sako.fabio.nasa.discovery.bean.Identify;
import sako.fabio.nasa.discovery.bean.Plateau;
import sako.fabio.nasa.discovery.bean.Probe;
import sako.fabio.nasa.discovery.enums.CardinalPoint;
import sako.fabio.nasa.discovery.exceptions.AlreadyCreatedException;
import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.exceptions.InvalidCommandException;
import sako.fabio.nasa.discovery.exceptions.ObjectNasaNotFound;
import sako.fabio.nasa.discovery.manager.interfaces.DiscoveryManagerInterface;

public class DiscoveryManagerTest {
	private DiscoveryManagerInterface discoveryManager;
	
	@Before
	public void setUp(){
		discoveryManager = new DiscoveryManager(new Plateau(5, 5));
	}
	
	@Test
	public void testAddProbe() throws BordersInvasionException, BusyPlaceException, AlreadyBoundException, AlreadyCreatedException{
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>("probe_1");
		CardinalPoint cardinalPointInitial = CardinalPoint.N;
		Coordination coordination = new Coordination(x, y);
		Probe probe = discoveryManager.addProbe(id, coordination, cardinalPointInitial);
		Assert.assertNotNull(probe);
		Assert.assertEquals(x, probe.getCoordination().getX());
		Assert.assertEquals(y, probe.getCoordination().getY());
	}
	
	@Test(expected= AlreadyCreatedException.class)
	public void testAddProbeAlreadyCreated() throws BordersInvasionException, BusyPlaceException, AlreadyBoundException, AlreadyCreatedException{
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>("probe_1");
		CardinalPoint cardinalPointInitial = CardinalPoint.N;
		Coordination coordination = new Coordination(x, y);

		discoveryManager.addProbe(id, coordination, cardinalPointInitial);
		discoveryManager.addProbe(id, coordination, cardinalPointInitial);
		
	}
	
	@Test
	public void testCommand() throws BordersInvasionException, BusyPlaceException, InvalidCommandException, AlreadyBoundException, AlreadyCreatedException, ObjectNasaNotFound{
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>("probe_1");
		CardinalPoint cardinalPointInitial = CardinalPoint.N;
		Coordination coordination = new Coordination(x, y);

		discoveryManager.addProbe(id, coordination, cardinalPointInitial);

		List<String> commands = Arrays.asList("L","M","L","M","L","M","L","M","M");
		Probe probe = discoveryManager.command(id, commands);
		int xExpected = 1;
		int yExpected = 3;
		CardinalPoint cardinalPointExpected = CardinalPoint.N;
		Assert.assertEquals(xExpected, probe.getCoordination().getX());
		Assert.assertEquals(yExpected, probe.getCoordination().getY());
		Assert.assertEquals(cardinalPointInitial, cardinalPointExpected);
	}
	
	@Test(expected=InvalidCommandException.class)
	public void testCommandInvalid() throws BordersInvasionException, BusyPlaceException, InvalidCommandException, AlreadyBoundException, AlreadyCreatedException, ObjectNasaNotFound{
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>("probe_1");
		CardinalPoint cardinalPointInitial = CardinalPoint.N;
		Coordination coordination = new Coordination(x, y);

		discoveryManager.addProbe(id, coordination, cardinalPointInitial);
		List<String> commands = Arrays.asList("L","M","L","M","L","x","L","M","M");
		discoveryManager.command(id, commands);
	}
	
	@Test(expected=BusyPlaceException.class)
	public void testCommandConflictsPlaces() throws BordersInvasionException, BusyPlaceException, InvalidCommandException, AlreadyBoundException, AlreadyCreatedException, ObjectNasaNotFound{
		int x = 1;
		int y = 2;
		Identify<String> id = new Identify<String>("probe_1");
		CardinalPoint cardinalPointInitial = CardinalPoint.N;
		Coordination coordination = new Coordination(x, y);
		discoveryManager.addProbe(id, coordination, cardinalPointInitial);
		
		int x2 = 1;
		int y2 = 3;
		Identify<String> id2 = new Identify<String>("probe_2");
		CardinalPoint cardinalPointInitial2 = CardinalPoint.N;
		Coordination coordination2 = new Coordination(x2, y2);
		discoveryManager.addProbe(id2, coordination2, cardinalPointInitial2);
		
		List<String> commands = Arrays.asList("L","M","L","M","L","M","L","M","M");
		discoveryManager.command(id, commands);
	}
	
	@Test(expected=ObjectNasaNotFound.class)
	public void testCommandProbeNotFound() throws BordersInvasionException, BusyPlaceException, InvalidCommandException, AlreadyBoundException, AlreadyCreatedException, ObjectNasaNotFound{
		Identify<String> id = new Identify<String>("probe_1");		
		List<String> commands = Arrays.asList("L","M","L","M","L","M","L","M","M");
		discoveryManager.command(id, commands);
	}
	
	
}
