package dev.pe.app.models.shopcart;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "vw_all_shopcart") @Data
public class ShopcartView {
  @Id @Column(name = "id_user_buyer")
  private UUID id;
  private String productName;
  private double price;
  private int quantity;
  private LocalDateTime dateAdded;
  private double total;
}
