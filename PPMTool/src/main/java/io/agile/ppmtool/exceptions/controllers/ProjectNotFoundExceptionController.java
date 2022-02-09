package io.agile.ppmtool.exceptions.controllers;

import io.agile.ppmtool.exceptions.ProjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ProjectNotFoundExceptionController {

    @ExceptionHandler(ProjectNotFoundException.class)
    protected ResponseEntity<Object> projectNotFoundExceptionHandler(ProjectNotFoundException ex) {
        log.error(ex.getErrorMessage());
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.NOT_FOUND);
    }
}
