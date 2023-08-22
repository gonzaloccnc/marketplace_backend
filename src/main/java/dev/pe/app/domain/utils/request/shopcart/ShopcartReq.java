package dev.pe.app.domain.utils.request.shopcart;

import lombok.Data;

import java.util.UUID;

@Data
public class ShopcartReq {
  private UUID idUserBuyer;
  private UUID idProduct;
  private int quantity;
}
