package com.jakub.chatbot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class NotFoundException extends Exception {
	public NotFoundException(String message){
		super(message);
	}

	public NotFoundException(String message, Throwable cause){
		super(message,cause);
	}
}
