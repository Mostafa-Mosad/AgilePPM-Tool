package io.agile.ppmtool.exceptions.controllers;

import io.agile.ppmtool.exceptions.ProjectIdentifierException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ProjectIdentifierExceptionController  {

    @ExceptionHandler(ProjectIdentifierException.class)
    protected ResponseEntity<Object> handleProjectIdentifierException(ProjectIdentifierException ex) {
        log.error(ex.getErrorMessage());
        return new ResponseEntity<Object>(ex.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }
}
