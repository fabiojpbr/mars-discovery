package sako.fabio.nasa.discovery.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sako.fabio.nasa.discovery.enums.Direction;
import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.model.Coordination;
import sako.fabio.nasa.discovery.model.Plateau;
import sako.fabio.nasa.discovery.model.Probe;
/**
 * Testes unitários da Sonda
 * @author fabio
 *
 */
public class TestProbe {
	private static final int INIT_X = 1;
	private static final int INIT_Y = 1;
	private static final int INIT_HEIGHT = 5;
	private static final int INIT_WIDTH = 5;
	private Plateau plateau;
	
	@Before
	/**
	 * Configurar o ambiente antes de cada teste
	 */
	public void setUp(){
		this.plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
	}

	@Test
	public void testMoveToNorth(){
		
		Probe probe = new Probe(null, new Coordination(INIT_X, INIT_Y) , Direction.N, plateau );
		probe.move();
		int expected = 2;
		Assert.assertEquals(expected, probe.getCoordination().getY());
	}
	
	@Test
	public void testMoveToEast() throws BordersInvasionException, BusyPlaceException{
		Probe probe = new Probe(null, new Coordination(INIT_X, INIT_Y), Direction.E, plateau );
		probe.move();
		int expected = 2;
		Assert.assertEquals(expected, probe.getCoordination().getX());
	}
	@Test
	public void testMoveToSouth() throws BordersInvasionException, BusyPlaceException{
		Probe probe = new Probe(null, new Coordination(INIT_X, INIT_Y), Direction.S, plateau );
		probe.move();
		int expected = 0;
		Assert.assertEquals(expected, probe.getCoordination().getY());
	}
	
	@Test
	public void testMoveToWeast() throws BordersInvasionException, BusyPlaceException{

		Probe probe = new Probe(null, new Coordination(INIT_X, INIT_Y), Direction.W, plateau );
		probe.move();
		int expected = 0;
		Assert.assertEquals(expected, probe.getCoordination().getX());
	}
	
	@Test
	public void testProbeMoveLeftSequences() throws BordersInvasionException, BusyPlaceException{

		int x = 1;
		int y = 2;
		Probe probe = new Probe(null, new Coordination(x, y), Direction.N, plateau );
		//LMLMLMLMM
		probe.turnLeft();
		probe.move();
		probe.turnLeft();
		probe.move();
		probe.turnLeft();
		probe.move();
		probe.turnLeft();
		probe.move();
		probe.move();
		//1 3 N
		int xExpected = 1;
		int yExpected = 3;
		Direction directionExpected = Direction.N;
		
		Assert.assertEquals(xExpected, probe.getCoordination().getX());
		Assert.assertEquals(yExpected, probe.getCoordination().getY());
		Assert.assertEquals(directionExpected, probe.getDirection());
	}
	
	@Test
	public void testProbeMoveRightSequences() throws BordersInvasionException, BusyPlaceException{
		//3 3 E
		int x = 3;
		int y = 3;
		Probe probe = new Probe(null, new Coordination(x, y), Direction.E, plateau );
		//MMRMMRMRRM
		probe.move();
		probe.move();
		probe.turnRight();
		probe.move();
		probe.move();
		probe.turnRight();
		probe.move();
		probe.turnRight();
		probe.turnRight();
		probe.move();
		//5 1 E
		int xExpected = 5;
		int yExpected = 1;
		Direction directionExpected = Direction.E;
		
		Assert.assertEquals(xExpected, probe.getCoordination().getX());
		Assert.assertEquals(yExpected, probe.getCoordination().getY());
		Assert.assertEquals(directionExpected, probe.getDirection());
	}
	@Test(expected=BordersInvasionException.class)
	public void testProveMoveBeyondBorderSouthShouldThrowsException() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		int x = 0;
		int y = 0;
		Probe probe = new Probe(null, new Coordination(x, y), Direction.S, plateau );
		probe.move();
	}
	
	@Test(expected=BordersInvasionException.class)
	public void testProveMoveBeyondBorderNorthShouldThrowsException() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		int x = 0;
		int y = 5;
		Probe probe = new Probe(null, new Coordination(x, y), Direction.N, plateau );
		probe.move();
	}
	
	@Test(expected=BordersInvasionException.class)
	public void testProveMoveBeyondBorderWestShouldThrowsException() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		int x = 0;
		int y = 0;
		Probe probe = new Probe(null, new Coordination(x, y), Direction.W, plateau );
		probe.move();
	}
	
	@Test(expected=BordersInvasionException.class)
	public void testProveMoveBeyondBorderEASTShouldThrowsException() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		int x = 5;
		int y = 0;
		Probe probe = new Probe(null, new Coordination(x, y), Direction.E, plateau );
		probe.move();
	}
}
