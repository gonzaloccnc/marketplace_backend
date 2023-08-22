package dev.pe.app.domain.utils.request.shopcart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ShopcartProductReq {
  private UUID idProduct;
  private int quantity;
}
