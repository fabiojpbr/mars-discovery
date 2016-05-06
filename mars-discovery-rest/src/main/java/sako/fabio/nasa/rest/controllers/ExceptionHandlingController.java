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
import sako.fabio.nasa.discovery.exceptions.ObjectNasaNotFoundException;
import sako.fabio.nasa.discovery.exceptions.ValidatorException;
import sako.fabio.nasa.rest.model.ErrorInfo;

/**
 * Classe Controller para lidar com os erros do sistema
 * @author fabio
 *
 */
@ControllerAdvice
public class ExceptionHandlingController {
	
	
	/**
	 * Lançado quando o objeto já existe
	 * @param req
	 * @param exception
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.CONFLICT) // 409
	@ExceptionHandler(AlreadyCreatedException.class)
	@ResponseBody
	public ErrorInfo alreadyExists(HttpServletRequest req, Exception exception) {
		String errorURL = req.getRequestURL().toString();
		String message = exception.getMessage();
		ErrorInfo errorInfo = new ErrorInfo(errorURL);
		errorInfo.addMessage(message);
		return errorInfo ;
	}
	
	/**
	 * Lançado quando não encontra o recurso
	 * @param req
	 * @param exception
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(ObjectNasaNotFoundException.class)
	@ResponseBody
	public ErrorInfo notFound(HttpServletRequest req, Exception exception){
		String errorURL = req.getRequestURL().toString();
		String message = exception.getMessage();
		ErrorInfo errorInfo = new ErrorInfo(errorURL);
		errorInfo.addMessage(message);
		return errorInfo ;
	}
	

	/**
	 * Quando o objeto não conseguiu movimentar pois encontrou um obstaculo
	 * @param req
	 * @param exception
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler({BordersInvasionException.class, BusyPlaceException.class})
	@ResponseBody
	public ErrorInfo notAllowed(HttpServletRequest req, Exception exception){
		String errorURL = req.getRequestURL().toString();
		String message = exception.getMessage();
		ErrorInfo errorInfo = new ErrorInfo(errorURL);
		errorInfo.addMessage(message);
		return errorInfo ;
	}
	/**
	 * Lançado quando informa um valor inválido
	 * @param req
	 * @param exception
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidatorException.class)
	@ResponseBody
	public ErrorInfo validatorException(HttpServletRequest req, ValidatorException exception){
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL);
		errorInfo.addAllMessage(exception.getMessages());
		return errorInfo ;
	}
	
	
}
