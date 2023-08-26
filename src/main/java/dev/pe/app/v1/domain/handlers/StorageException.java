package dev.pe.app.v1.domain.handlers;

public class StorageException extends RuntimeException {
  public StorageException(String message){
    super(message);
  }

  public StorageException(String message, Throwable throwable){
    super(message, throwable);
  }
}
