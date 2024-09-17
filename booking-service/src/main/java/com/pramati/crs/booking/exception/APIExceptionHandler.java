package com.pramati.crs.booking.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pramati.crs.booking.dto.MessageDTO;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MessageDTO> handleException(Exception e) {
		String errorMessage = e.getMessage();
		if (e instanceof NullPointerException) {
			errorMessage = errorMessage != null ? errorMessage : "INTERNAL_SERVER_ERROR";
		}
		return new ResponseEntity<>(new MessageDTO(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class, })
	public ResponseEntity<MessageDTO> handleDataValidationException(Exception e) {
		String errorMessage = null;

		if (e instanceof BindException) {
			BindException be = (BindException) e;
			errorMessage = be.getBindingResult().getFieldError().getDefaultMessage();
		} else if (e instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException me = (MethodArgumentNotValidException) e;
			errorMessage = me.getBindingResult().getFieldError().getDefaultMessage();
      }
		return new ResponseEntity<>(new MessageDTO(errorMessage), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(APIException.class)
	public ResponseEntity<MessageDTO> handleDataValidationException(APIException e) {
		return new ResponseEntity<>(new MessageDTO(e.getMessage()), e.getStatus());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<MessageDTO> handleAccessDeniedException(AccessDeniedException e) {
		return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.FORBIDDEN);
	}
}
