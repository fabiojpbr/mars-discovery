package sako.fabio.nasa.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import sako.fabio.nasa.discovery.exceptions.AlreadyCreatedException;
import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.exceptions.InvalidCommandException;
import sako.fabio.nasa.discovery.exceptions.ObjectNasaNotFoundException;

public class ExceptionHandlingController {
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Already Created") // 409
	@ExceptionHandler(AlreadyCreatedException.class)
	public void conflict() {

	}
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
	@ExceptionHandler(ObjectNasaNotFoundException.class)
	public void notFound(ObjectNasaNotFoundException exception){
		
	}
	
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid Command")
	@ExceptionHandler(InvalidCommandException.class)
	public void invalidCommand(){
		
	}
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "")
	@ExceptionHandler({BordersInvasionException.class, BusyPlaceException.class})
	public void notAllowed(){
		
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public void illegalArgument(){
		
	}
}
