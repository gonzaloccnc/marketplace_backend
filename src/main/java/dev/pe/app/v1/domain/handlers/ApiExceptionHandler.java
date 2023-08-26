package dev.pe.app.v1.domain.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.pe.app.v1.domain.utils.responses.ErrorInfo;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.id.IdentifierGenerationException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;

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
            .error(ex.getMessage())
            .url(req.getRequestURI())
            .build()
    );
  }

  @ExceptionHandler(JsonProcessingException.class)
  @Order(2)
  ResponseEntity<ErrorInfo> handleParseJson(HttpServletRequest req, JsonProcessingException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(ex.getOriginalMessage())
            .status(HttpStatus.BAD_REQUEST.value())
            .build()
    );
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @Order(3)
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
  @Order(4)
  ResponseEntity<ErrorInfo> handleViolateConstraint(HttpServletRequest req, ConstraintViolationException ex){
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ex.getSQLException().getMessage())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .build()
    );
  }

  @ExceptionHandler(IdentifierGenerationException.class)
  @Order(5)
  ResponseEntity<ErrorInfo> handleIdentifierGeneration(HttpServletRequest req, IdentifierGenerationException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ex.getMessage())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .build()
    );
  }

  @ExceptionHandler(FileSizeLimitExceededException.class)
  @Order(6)
  ResponseEntity<ErrorInfo> handleSizeLimit(HttpServletRequest req, FileSizeLimitExceededException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ex.getMessage())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .build()
    );
  }

  @ExceptionHandler(MissingServletRequestPartException.class)
  @Order(7)
  ResponseEntity<ErrorInfo> handleMissingRequestPart(HttpServletRequest req, MissingServletRequestPartException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ex.getMessage())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .build()
    );
  }

  @ExceptionHandler(MultipartException.class)
  @Order(8)
  ResponseEntity<ErrorInfo> handleMultipart(HttpServletRequest req, MultipartException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ex.getMessage())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .build()
    );
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @Order(9)
  ResponseEntity<ErrorInfo> handleNoReadable(HttpServletRequest req, HttpMessageNotReadableException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ex.getMessage())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .build()
    );
  }

  @ExceptionHandler(AccountStatusException.class)
  ResponseEntity<ErrorInfo> handleAccountStatus(HttpServletRequest req,AccountStatusException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .status(HttpStatus.UNAUTHORIZED.value())
            .message(ex.getMessage())
            .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
            .build()
    );
  }

  @ExceptionHandler(JwtException.class)
  ResponseEntity<ErrorInfo> handleJwt(HttpServletRequest req, JwtException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .status(HttpStatus.UNAUTHORIZED.value())
            .message(ex.getMessage())
            .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
            .build()
    );
  }

  @ExceptionHandler(AccessDeniedException.class)
  ResponseEntity<ErrorInfo> handleAccessDenied(HttpServletRequest req, AccessDeniedException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .status(HttpStatus.FORBIDDEN.value())
            .message(ex.getMessage())
            .error(HttpStatus.FORBIDDEN.getReasonPhrase())
            .build()
    );
  }

/*
  @ExceptionHandler(Exception.class)
  @Order(100)
  ResponseEntity<ErrorInfo> handleInternalError(HttpServletRequest req) {
    return ResponseEntity.status(500).body(
        ErrorInfo
            .builder()
            .url(req.getRequestURI())
            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message("An internal error occurred")
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
