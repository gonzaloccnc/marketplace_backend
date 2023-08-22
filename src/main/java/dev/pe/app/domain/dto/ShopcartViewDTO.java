package dev.pe.app.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data @Builder
public class ShopcartViewDTO {
  private UUID idUserBuyer;
  private UUID idProduct;

  private String productName;
  private double price;
  private int quantity;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dateAdded;

  private double total;
  private String photoMd;
  private String photoLg;
}
