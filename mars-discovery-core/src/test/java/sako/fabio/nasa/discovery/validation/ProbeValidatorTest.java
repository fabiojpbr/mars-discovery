package sako.fabio.nasa.discovery.validation;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import sako.fabio.nasa.discovery.enums.Direction;
import sako.fabio.nasa.discovery.exceptions.ValidatorException;
import sako.fabio.nasa.discovery.model.Coordination;
import sako.fabio.nasa.discovery.model.Identify;
import sako.fabio.nasa.discovery.model.Probe;
import sako.fabio.nasa.discovery.validation.ProbeValidator;
import sako.fabio.nasa.discovery.validation.interfaces.Validator;

public class ProbeValidatorTest {
	
	private Validator<Probe> validator;
	
	@Before
	public void setUp(){
		this.validator = new ProbeValidator();
	}
	
	@Test
	public void testCheckProbeNull(){
		ValidatorException exception = null;
		try{
			validator.check(null);
		}catch(ValidatorException e){
			exception = e;
		}
		int sizeExpected = 1;
		Assert.assertNotNull(exception);
		Assert.assertEquals(sizeExpected, exception.getMessages().size());
		
	}
	
	@Test
	public void testCheckNotError(){
		ValidatorException exception = null;
		try{
			Probe probe = new Probe(new Identify<String>("Name"), new Coordination(0, 0), Direction.E);
			validator.check(probe);
		}catch(ValidatorException e){
			exception = e;
		}
		Assert.assertNull(exception);

		
	}
	
	@Test
	public void testCheckProbeNameCoordinationDirectionNull(){
		ValidatorException exception = null;
		Probe probe = new Probe(null, null, null);
		try{
			validator.check(probe);
		}catch(ValidatorException e){
			exception = e;
		}
		int sizeExpected = 3;
		Assert.assertNotNull(exception);
		Assert.assertEquals(sizeExpected, exception.getMessages().size());
		
	}
	
	@Test
	public void testCheckProbeNameNull(){
		ValidatorException exception = null;
		Probe probe = new Probe(null, new Coordination(0, 0), Direction.N);
		try{
			validator.check(probe);
		}catch(ValidatorException e){
			exception = e;
		}
		int sizeExpected = 1;
		Assert.assertNotNull(exception);
		Assert.assertEquals(sizeExpected, exception.getMessages().size());
		
	}
	
	@Test
	public void testCheckProbeNameEmpty(){
		ValidatorException exception = null;
		Probe probe = new Probe(new Identify<String>(" "), new Coordination(0, 0), Direction.N);
		try{
			validator.check(probe);
		}catch(ValidatorException e){
			exception = e;
		}
		int sizeExpected = 1;
		Assert.assertNotNull(exception);
		Assert.assertEquals(sizeExpected, exception.getMessages().size());
		
	}
	
	@Test
	public void testCheckProbeCoordinationNull(){
		ValidatorException exception = null;
		Probe probe = new Probe(new Identify<String>("Name"), null, Direction.N);
		try{
			validator.check(probe);
		}catch(ValidatorException e){
			exception = e;
		}
		int sizeExpected = 1;
		Assert.assertNotNull(exception);
		Assert.assertEquals(sizeExpected, exception.getMessages().size());
		
	}
	
	@Test
	public void testCheckProbeDirectionNull(){
		ValidatorException exception = null;
		Probe probe = new Probe(new Identify<String>("Name"), new Coordination(0, 0), null);
		try{
			validator.check(probe);
		}catch(ValidatorException e){
			exception = e;
		}
		int sizeExpected = 1;
		Assert.assertNotNull(exception);
		Assert.assertEquals(sizeExpected, exception.getMessages().size());
		
	}
}
