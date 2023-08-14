package dev.pe.app.domain.utils.responses;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@Builder @Data
public class PageableResponse<T> {
  private List<T> data;
  private String next;
  private String prev;
  private int page;
  private int pageSize;
  private Long hints;
  private int totalPages;
  private int status;
  private String error;
}
