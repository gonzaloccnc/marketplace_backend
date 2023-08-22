package dev.pe.app.models.shopcart;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "vw_all_shopcart") @Data
public class ShopcartView {
  @Id
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
