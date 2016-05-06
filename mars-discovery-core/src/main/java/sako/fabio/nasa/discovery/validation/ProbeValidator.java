package sako.fabio.nasa.discovery.validation;

import sako.fabio.nasa.discovery.exceptions.ValidatorException;
import sako.fabio.nasa.discovery.model.Probe;
import sako.fabio.nasa.discovery.validation.interfaces.Validator;
/**
 * Classe que valida a Sonda
 * @author fabio
 *
 */
public class ProbeValidator implements Validator<Probe> {

	@Override
	public void check(Probe t) {
		ValidatorException exception = new ValidatorException();
		if(t == null){
			exception.addMessage("Probe cannot be null");
			throw exception;
		}
		if(t.getName() == null || t.getName().getId() == null || t.getName().getId().trim().length() == 0){
			exception.addMessage("Probe name cannot be null or empty!");
		}
		if(t.getCoordination() == null){
			exception.addMessage("Coordination Probe cannot be null or empty!");
		}
		
		if(t.getDirection() == null){
			exception.addMessage("Direction Probe cannot be null or empty!");
		}
		
		
		if(!exception.getMessages().isEmpty()){
			throw exception;
		}
		
	}

}
