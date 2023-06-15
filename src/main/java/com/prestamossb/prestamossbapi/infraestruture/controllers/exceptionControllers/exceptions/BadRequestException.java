package com.prestamossb.prestamossbapi.infraestruture.controllers.exceptionControllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    public BadRequestException(List<String> message){
        super(String.join(", ",message));
    }
}
