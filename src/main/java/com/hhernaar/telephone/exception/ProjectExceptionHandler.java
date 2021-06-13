package com.hhernaar.telephone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.hhernaar.telephone.dto.ResponseApiDto;
import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class ProjectExceptionHandler {

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<ResponseApiDto> missingRequestHeaderException(
      MissingRequestHeaderException ex) {
    log.error("missingRequestHeaderException");
    log.error(ex);
    ex.printStackTrace();
    return new ResponseEntity<>(new ResponseApiDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ResponseApiDto> badRequest(BadRequestException ex) {
    log.error("BadRequestExceptoin");
    log.error(ex);
    ex.printStackTrace();
    if (ex.getErrores() != null && !ex.getErrores().isEmpty()) {
      return new ResponseEntity<>(
          new ResponseApiDto(HttpStatus.BAD_REQUEST.value(), ex.getErrores()),
          HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(new ResponseApiDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ResponseApiDto> resourceNotFoundException(ResourceNotFoundException ex) {
    log.error("ResourceNotFoundException");
    log.error(ex);
    ex.printStackTrace();
    return new ResponseEntity<>(new ResponseApiDto(HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseApiDto> exception(Exception ex) {
    log.error("Exception");
    log.error(ex);
    ex.printStackTrace();
    return new ResponseEntity<>(new ResponseApiDto(HttpStatus.INTERNAL_SERVER_ERROR),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
