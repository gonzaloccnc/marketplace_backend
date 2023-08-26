package dev.pe.app.v1.domain.utils.request.shopcart;

import lombok.Data;

import java.util.UUID;

@Data
public class ShopcartReq {
  private UUID idUserBuyer;
  private UUID idProduct;
  private int quantity;
}
