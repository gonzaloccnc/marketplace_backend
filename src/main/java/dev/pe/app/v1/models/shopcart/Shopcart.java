package dev.pe.app.v1.models.shopcart;

import jakarta.persistence.*;
import lombok.*;

@Entity @Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Shopcart {

  @EmbeddedId
  private ShopcartKey id;
  private int quantity;
}
