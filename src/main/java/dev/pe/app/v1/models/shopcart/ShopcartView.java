package dev.pe.app.v1.models.shopcart;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "vw_all_shopcart") @Data
public class ShopcartView {

  @EmbeddedId
  private ShopcartKey id;

  private String productName;
  private double price;
  private int quantity;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dateAdded;

  private double total;
  private String photoMd;
  private String photoLg;
  private UUID idSeller;
}
