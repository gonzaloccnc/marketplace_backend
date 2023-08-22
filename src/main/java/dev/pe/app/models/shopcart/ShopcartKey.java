package dev.pe.app.models.shopcart;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable @Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ShopcartKey implements Serializable {
  private UUID idUserBuyer;
  private UUID idProduct;
}
