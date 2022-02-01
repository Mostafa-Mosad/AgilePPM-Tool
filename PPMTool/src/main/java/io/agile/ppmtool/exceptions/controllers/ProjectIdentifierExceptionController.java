package io.agile.ppmtool.exceptions.controllers;

import io.agile.ppmtool.exceptions.ProjectIdentifierException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProjectIdentifierExceptionController  {

    @ExceptionHandler(ProjectIdentifierException.class)
    protected ResponseEntity<Object> handleProjectIdentifierException(Exception ex) {
        return new ResponseEntity<Object>("Project Identifier already exist, please use a different one!", HttpStatus.BAD_REQUEST);
    }
}
