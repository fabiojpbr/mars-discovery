package sako.fabio.nasa.discovery.bean;

import org.junit.Assert;
import org.junit.Test;

import sako.fabio.nasa.discovery.bean.Plateau;
import sako.fabio.nasa.discovery.bean.Probe;
import sako.fabio.nasa.discovery.enums.CardinalPoint;
import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;

public class TestProbe {
	private static final int INIT_X = 1;
	private static final int INIT_Y = 1;
	private static final int INIT_HEIGHT = 5;
	private static final int INIT_WIDTH = 5;

	
	@Test
	public void testMoveToNorth() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		Probe probe = new Probe(null, INIT_X, INIT_Y, CardinalPoint.N, plateau );
		probe.move();
		int expected = 2;
		Assert.assertEquals(expected, probe.getCoordination().getY());
	}
	
	@Test
	public void testMoveToEast() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		Probe probe = new Probe(null, INIT_X, INIT_Y, CardinalPoint.E, plateau );
		probe.move();
		int expected = 2;
		Assert.assertEquals(expected, probe.getCoordination().getX());
	}
	@Test
	public void testMoveToSouth() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		Probe probe = new Probe(null, INIT_X, INIT_Y, CardinalPoint.S, plateau );
		probe.move();
		int expected = 0;
		Assert.assertEquals(expected, probe.getCoordination().getY());
	}
	
	@Test
	public void testMoveToWeast() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		Probe probe = new Probe(null, INIT_X, INIT_Y, CardinalPoint.W, plateau );
		probe.move();
		int expected = 0;
		Assert.assertEquals(expected, probe.getCoordination().getX());
	}
	
	@Test
	public void testProbeMoveLeftSequences() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		int x = 1;
		int y = 2;
		Probe probe = new Probe(null, x, y, CardinalPoint.N, plateau );
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
		CardinalPoint cardinalPointExpected = CardinalPoint.N;
		
		Assert.assertEquals(xExpected, probe.getCoordination().getX());
		Assert.assertEquals(yExpected, probe.getCoordination().getY());
		Assert.assertEquals(cardinalPointExpected, probe.getCardinalPoint());
	}
	
	@Test
	public void testProbeMoveRightSequences() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		//3 3 E
		int x = 3;
		int y = 3;
		Probe probe = new Probe(null, x, y, CardinalPoint.E, plateau );
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
		CardinalPoint cardinalPointExpected = CardinalPoint.E;
		
		Assert.assertEquals(xExpected, probe.getCoordination().getX());
		Assert.assertEquals(yExpected, probe.getCoordination().getY());
		Assert.assertEquals(cardinalPointExpected, probe.getCardinalPoint());
	}
	@Test(expected=BordersInvasionException.class)
	public void testProveMoveBeyondBorderSouthShouldThrowsException() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		int x = 0;
		int y = 0;
		Probe probe = new Probe(null, x, y, CardinalPoint.S, plateau );
		probe.move();
	}
	
	@Test(expected=BordersInvasionException.class)
	public void testProveMoveBeyondBorderNorthShouldThrowsException() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		int x = 0;
		int y = 5;
		Probe probe = new Probe(null, x, y, CardinalPoint.N, plateau );
		probe.move();
	}
	
	@Test(expected=BordersInvasionException.class)
	public void testProveMoveBeyondBorderWestShouldThrowsException() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		int x = 0;
		int y = 0;
		Probe probe = new Probe(null, x, y, CardinalPoint.W, plateau );
		probe.move();
	}
	
	@Test(expected=BordersInvasionException.class)
	public void testProveMoveBeyondBorderEASTShouldThrowsException() throws BordersInvasionException, BusyPlaceException{
		Plateau plateau = new Plateau(INIT_HEIGHT, INIT_WIDTH);
		int x = 5;
		int y = 0;
		Probe probe = new Probe(null, x, y, CardinalPoint.E, plateau );
		probe.move();
	}
}
