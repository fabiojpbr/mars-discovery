package sako.fabio.nasa.discovery.enums;

import org.junit.Test;

import junit.framework.Assert;
/**
 * Testes unitários 
 * @author fabio
 *
 */
public class TestDirectionPoint {
	
	
	@Test
	public void testNextRight(){
		Direction expected = Direction.E;
		Direction nextDirection = Direction.N.getNextRight();
		Assert.assertEquals(expected, nextDirection);
	}
	
	@Test
	public void testNextRightExecuteEndArray(){
		Direction expected = Direction.N;
		Direction nextDirection = Direction.W.getNextRight();
		Assert.assertEquals(expected, nextDirection);
	}
	
	@Test
	public void testNextLeft(){
		Direction expected = Direction.E;
		Direction nextDirection = Direction.S.getNextLeft();
		Assert.assertEquals(expected, nextDirection);
	}
	
	@Test
	public void testNextLeftEndArray(){
		Direction expected = Direction.W;
		Direction nextDirection = Direction.N.getNextLeft();
		Assert.assertEquals(expected, nextDirection);
	}
}
