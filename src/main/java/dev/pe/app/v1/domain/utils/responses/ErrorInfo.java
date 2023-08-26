package dev.pe.app.v1.domain.utils.responses;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class ErrorInfo {
  private String url;
  private int status;
  private String error;
  private String message;
}
