package sako.fabio.nasa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import sako.fabio.nasa.discovery.exceptions.AlreadyCreatedException;

public class ExceptionHandlingController {
	@ResponseStatus(value=HttpStatus.CONFLICT, reason="Already Created")  // 409
	  @ExceptionHandler(AlreadyCreatedException.class)
	  public void conflict() {

	  }
}
