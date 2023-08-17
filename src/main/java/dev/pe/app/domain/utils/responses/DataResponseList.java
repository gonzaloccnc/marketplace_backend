package dev.pe.app.domain.utils.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder @Data
public class DataResponseList<T> {
  private List<T> data;
  private int status;
  private String error;
  private String message;
}
