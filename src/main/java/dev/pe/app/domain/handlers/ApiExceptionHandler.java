package dev.pe.app.domain.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.pe.app.domain.utils.responses.ErrorInfo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(NoHandlerFoundException.class)
  @Order(1)
  ResponseEntity<ErrorInfo> handleNotFoundException(NoHandlerFoundException ex, HttpServletRequest req) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        ErrorInfo
            .builder()
            .message(HttpStatus.NOT_FOUND.getReasonPhrase())
            .status(HttpStatus.NOT_FOUND.value())
            .error(ex.getLocalizedMessage())
            .url(req.getRequestURI())
            .build()
    );
  }

  @ExceptionHandler(NumberFormatException.class)
  @Order(2)
  ResponseEntity<ErrorInfo> handleNumberFormatException(HttpServletRequest req, NumberFormatException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ex.getLocalizedMessage())
            .build()
    );
  }

  @ExceptionHandler(SQLException.class)
  @Order(3)
  ResponseEntity<ErrorInfo> handleSqlThrows(HttpServletRequest req, SQLException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(ex.getLocalizedMessage())
            .url(req.getRequestURI())
            .status(HttpStatus.BAD_REQUEST.value())
            .build()
    );
  }

  @ExceptionHandler(JsonProcessingException.class)
  @Order(4)
  ResponseEntity<ErrorInfo> handleParseJson(HttpServletRequest req, JsonProcessingException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(ex.getMessage())
            .status(HttpStatus.BAD_REQUEST.value())
            .build()
    );
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @Order(5)
  ResponseEntity<ErrorInfo> handleConverterString(HttpServletRequest req, IllegalArgumentException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo.builder()
            .url(req.getRequestURI())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(ex.getMessage())
            .status(HttpStatus.BAD_REQUEST.value())
            .build()
    );
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @Order(6)
  ResponseEntity<ErrorInfo> handleViolateConstraint(HttpServletRequest req, ConstraintViolationException ex){
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ex.getLocalizedMessage())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .build()
    );
  }

  /// TODO when this is activated some methods do not work and arrive at this
  /*
  @ExceptionHandler(Exception.class)
  @Order(7)
  ResponseEntity<ErrorInfo> handleJWT(HttpServletRequest req, Exception ex) {
    var status = getStatus(req);

    return ResponseEntity.status(status).body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .error(status.getReasonPhrase())
            .status(status.value())
            .message(ex.getLocalizedMessage())
            .build()
    );
  }
   */

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer code = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if(code == null) return HttpStatus.INTERNAL_SERVER_ERROR;

    HttpStatus status = HttpStatus.resolve(code);

    return (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR;
  }

}
