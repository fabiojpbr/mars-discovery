package sako.fabio.nasa.discovery.validation;

import sako.fabio.nasa.discovery.exceptions.ValidatorException;
import sako.fabio.nasa.discovery.model.Plateau;
import sako.fabio.nasa.discovery.validation.interfaces.Validator;
/**
 * Classe que valida os valores do Planalto
 * @author fabio
 *
 */
public class PlateauValidator implements Validator<Plateau> {

	@Override
	public void check(Plateau t) {
		ValidatorException exception = new ValidatorException();
		if(t == null){
			exception.addMessage("Plateau cannot be null");
			throw exception;
		}
		if(t.getName() == null || t.getName().getId() == null || t.getName().getId().trim().length() == 0){
			exception.addMessage("Plateau name cannot be null or empty!");
		}
		
		if(t.getHeight() < 1 || t.getWidth() < 1){
			exception.addMessage("height and width need to be greater than 0");
		}
		
		if(!exception.getMessages().isEmpty()){
			throw exception;
		}
	}

}
