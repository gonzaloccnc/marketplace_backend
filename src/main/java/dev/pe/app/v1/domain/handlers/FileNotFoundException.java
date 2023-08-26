package dev.pe.app.v1.domain.handlers;

public class FileNotFoundException extends RuntimeException {
  public FileNotFoundException(String message){
    super(message);
  }

  public FileNotFoundException(String message, Throwable throwable){
    super(message, throwable);
  }
}
