package dev.pe.app.models.shopcart;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ShopcartKey implements Serializable {
  private UUID idUserBuyer;
  private UUID idProduct;
}
