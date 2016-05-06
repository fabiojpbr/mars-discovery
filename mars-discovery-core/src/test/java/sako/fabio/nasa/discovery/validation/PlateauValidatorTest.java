package sako.fabio.nasa.discovery.validation;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import sako.fabio.nasa.discovery.exceptions.ValidatorException;
import sako.fabio.nasa.discovery.model.Identify;
import sako.fabio.nasa.discovery.model.Plateau;
import sako.fabio.nasa.discovery.validation.interfaces.Validator;

public class PlateauValidatorTest {
	
	private Validator<Plateau> validator;
	
	@Before
	public void setUp(){
		this.validator = new PlateauValidator();
	}
	
	@Test
	public void testCheckPlateauNull(){
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
			Plateau plateau = new Plateau(new Identify<String>("Name"), 10, 10);
			validator.check(plateau);
		}catch(ValidatorException e){
			exception = e;
		}
		Assert.assertNull(exception);

		
	}
	
	@Test
	public void testCheckPlateauNameNullAndHeightWidthZeros(){
		ValidatorException exception = null;
		Plateau plateau = new Plateau(null, 0, 0);
		try{
			
			validator.check(plateau);
		}catch(ValidatorException e){
			exception = e;
		}
		int sizeExpected = 2;
		Assert.assertNotNull(exception);
		Assert.assertEquals(sizeExpected, exception.getMessages().size());
		
	}
	
	@Test
	public void testCheckPlateauNameNull(){
		ValidatorException exception = null;
		Plateau plateau = new Plateau(null, 10, 10);
		try{
			validator.check(plateau);
		}catch(ValidatorException e){
			exception = e;
		}
		int sizeExpected = 1;
		Assert.assertNotNull(exception);
		Assert.assertEquals(sizeExpected, exception.getMessages().size());
		
	}
	
	@Test
	public void testCheckPlateauNameEmpty(){
		ValidatorException exception = null;
		Plateau plateau = new Plateau(new Identify<String>(" "), 10, 10);
		try{
			validator.check(plateau);
		}catch(ValidatorException e){
			exception = e;
		}
		int sizeExpected = 1;
		Assert.assertNotNull(exception);
		Assert.assertEquals(sizeExpected, exception.getMessages().size());
		
	}
	

	
	@Test
	public void testCheckPlateauHeightZero(){
		ValidatorException exception = null;
		Plateau plateau = new Plateau(new Identify<String>("Plateau"), 0, 10);
		try{
			validator.check(plateau);
		}catch(ValidatorException e){
			exception = e;
		}
		int sizeExpected = 1;
		Assert.assertNotNull(exception);
		Assert.assertEquals(sizeExpected, exception.getMessages().size());
		
	}
	
	@Test
	public void testCheckPlateauWidthZero(){
		ValidatorException exception = null;
		Plateau plateau = new Plateau(new Identify<String>("Plateau"), 10, 0);
		try{
			validator.check(plateau);
		}catch(ValidatorException e){
			exception = e;
		}
		int sizeExpected = 1;
		Assert.assertNotNull(exception);
		Assert.assertEquals(sizeExpected, exception.getMessages().size());
		
	}
}
