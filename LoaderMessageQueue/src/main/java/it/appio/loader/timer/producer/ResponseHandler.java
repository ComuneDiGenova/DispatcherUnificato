package it.appio.loader.timer.producer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Classe per generare la response delle chiamate REST
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
public class ResponseHandler {
	
	public static ResponseEntity<Object> generateResponse(Object responseObj, HttpHeaders httpHeaders, HttpStatus status) {
            return new ResponseEntity<Object>(responseObj, httpHeaders, status);
    }

}
