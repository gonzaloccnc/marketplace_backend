package dev.pe.app.v1.domain.utils.request.shopcart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ShopcartListReq {
  private List<ShopcartProductReq> products;
}
