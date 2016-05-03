package sako.fabio.nasa.discovery.enums;

import org.junit.Test;

import junit.framework.Assert;

public class TestCardinalPoint {
	
	
	@Test
	public void testNextRight(){
		Direction expected = Direction.E;
		Direction nextCardinal = Direction.N.getNextRight();
		Assert.assertEquals(expected, nextCardinal);
	}
	
	@Test
	public void testNextRightExecuteEndArray(){
		Direction expected = Direction.N;
		Direction nextCardinal = Direction.W.getNextRight();
		Assert.assertEquals(expected, nextCardinal);
	}
	
	@Test
	public void testNextLeft(){
		Direction expected = Direction.E;
		Direction nextCardinal = Direction.S.getNextLeft();
		Assert.assertEquals(expected, nextCardinal);
	}
	
	@Test
	public void testNextLeftEndArray(){
		Direction expected = Direction.W;
		Direction nextCardinal = Direction.N.getNextLeft();
		Assert.assertEquals(expected, nextCardinal);
	}
}
