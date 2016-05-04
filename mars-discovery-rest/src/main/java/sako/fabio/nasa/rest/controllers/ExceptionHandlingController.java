package sako.fabio.nasa.rest.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import sako.fabio.nasa.discovery.exceptions.AlreadyCreatedException;
import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.exceptions.InvalidCommandException;
import sako.fabio.nasa.discovery.exceptions.ObjectNasaNotFoundException;
import sako.fabio.nasa.rest.model.ErrorInfo;
@ControllerAdvice
public class ExceptionHandlingController {
	
	
	@ResponseStatus(value = HttpStatus.CONFLICT) // 409
	@ExceptionHandler(AlreadyCreatedException.class)
	@ResponseBody
	public ErrorInfo conflict(HttpServletRequest req, Exception exception) {
		String errorURL = req.getRequestURL().toString();
		String message = exception.getMessage();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, message);
		return errorInfo ;
	}
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(ObjectNasaNotFoundException.class)
	@ResponseBody
	public ErrorInfo notFound(HttpServletRequest req, Exception exception){
		String errorURL = req.getRequestURL().toString();
		String message = exception.getMessage();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, message);
		return errorInfo ;
	}
	
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(InvalidCommandException.class)
	@ResponseBody
	public ErrorInfo invalidCommand(HttpServletRequest req, Exception exception){
		String errorURL = req.getRequestURL().toString();
		String message = exception.getMessage();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, message);
		return errorInfo ;
	}
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler({BordersInvasionException.class, BusyPlaceException.class})
	@ResponseBody
	public ErrorInfo notAllowed(HttpServletRequest req, Exception exception){
		String errorURL = req.getRequestURL().toString();
		String message = exception.getMessage();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, message);
		return errorInfo ;
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	public ErrorInfo illegalArgument(HttpServletRequest req, Exception exception){
		String errorURL = req.getRequestURL().toString();
		String message = exception.getMessage();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, message);
		return errorInfo ;
	}
}
