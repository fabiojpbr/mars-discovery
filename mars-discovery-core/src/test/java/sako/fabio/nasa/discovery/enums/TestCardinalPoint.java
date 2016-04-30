package sako.fabio.nasa.discovery.enums;

import org.junit.Test;

import junit.framework.Assert;

public class TestCardinalPoint {
	
	
	@Test
	public void testNextRight(){
		CardinalPoint expected = CardinalPoint.E;
		CardinalPoint nextCardinal = CardinalPoint.N.getNextRight();
		Assert.assertEquals(expected, nextCardinal);
	}
	
	@Test
	public void testNextRightExecuteEndArray(){
		CardinalPoint expected = CardinalPoint.N;
		CardinalPoint nextCardinal = CardinalPoint.W.getNextRight();
		Assert.assertEquals(expected, nextCardinal);
	}
	
	@Test
	public void testNextLeft(){
		CardinalPoint expected = CardinalPoint.E;
		CardinalPoint nextCardinal = CardinalPoint.S.getNextLeft();
		Assert.assertEquals(expected, nextCardinal);
	}
	
	@Test
	public void testNextLeftEndArray(){
		CardinalPoint expected = CardinalPoint.W;
		CardinalPoint nextCardinal = CardinalPoint.N.getNextLeft();
		Assert.assertEquals(expected, nextCardinal);
	}
}
