package dev.pe.app.models.shopcart;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
public class Shopcart {

  @EmbeddedId
  private ShopcartKey id;
  private int quantity;
}
