package dev.pe.app.domain.utils.responses;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class DataResponse<T> {
  private T data;
  private int status;
  private String error;
  private String message;
}
