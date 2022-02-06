package io.agile.ppmtool.exceptions.controllers;

import io.agile.ppmtool.exceptions.ProjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProjectNotFoundExceptionController {

    @ExceptionHandler(ProjectNotFoundException.class)
    protected ResponseEntity<Object> projectNotFoundExceptionHandler(ProjectNotFoundException ex) {
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.NOT_FOUND);
    }
}
