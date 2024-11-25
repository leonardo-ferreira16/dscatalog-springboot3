package com.experts.project1.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.experts.project1.services.exceptions.DatabaseException;
import com.experts.project1.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus notFoundStatus = HttpStatus.NOT_FOUND; 
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(notFoundStatus.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(notFoundStatus).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
		HttpStatus badRequestStatus = HttpStatus.BAD_REQUEST; 
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(badRequestStatus.value());
		err.setError("Database exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(badRequestStatus).body(err);
	}
}
