package dev.pe.app.domain.handlers;

import dev.pe.app.domain.utils.responses.ErrorInfo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler({NoHandlerFoundException.class})
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

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(NumberFormatException.class)
  ResponseEntity<ErrorInfo> handleNumberFormatException(HttpServletRequest request, NumberFormatException ex) {
    return ResponseEntity.badRequest().body(
        ErrorInfo
            .builder()
            .url(request.getRequestURI())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ex.getLocalizedMessage())
            .build()
    );
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<ErrorInfo> handleAllErrors(HttpServletRequest request, Exception ex) {
    var status = getStatus(request);

    return ResponseEntity.status(status).body(
        ErrorInfo
            .builder()
            .url(request.getRequestURI())
            .error(status.getReasonPhrase())
            .status(status.value())
            .message(ex.getLocalizedMessage())
            .build()
    );
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer code = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if(code == null) return HttpStatus.INTERNAL_SERVER_ERROR;

    HttpStatus status = HttpStatus.resolve(code);

    return (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR;
  }

}
