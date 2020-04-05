package com.viafoura.Anagram.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private static Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

	@ExceptionHandler(InvalidStringException.class)
	public ResponseEntity<ErrorInfo> invaldString(InvalidStringException ex) {
		log.error("e", ex);
		return new ResponseEntity<>(
				new ErrorInfo("HTTP 404", "BAD REQUEST")
				, HttpStatus.BAD_REQUEST);
	}
	

}