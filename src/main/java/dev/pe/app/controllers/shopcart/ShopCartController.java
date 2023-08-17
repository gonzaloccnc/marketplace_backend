package dev.pe.app.controllers.shopcart;

import dev.pe.app.domain.utils.responses.DataResponseList;
import dev.pe.app.models.shopcart.ShopcartView;
import dev.pe.app.services.shopcart.ShopcartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController @RequestMapping("/shopcart") @RequiredArgsConstructor
public class ShopCartController {

  private final ShopcartService shopcartService;

  @GetMapping("/page/{userID}")
  ResponseEntity<?> index(
      @PageableDefault(size = 20) Pageable pageable,
      @PathVariable UUID userID,
      HttpServletRequest request
  ) {
      return ResponseEntity.ok().build();
  }

  @GetMapping("/{userID}")
  ResponseEntity<DataResponseList<ShopcartView>> getShopcart(@PathVariable UUID userID) {
    var message = shopcartService.findAll(userID);
    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @PostMapping("/{userID}")
  ResponseEntity<?> addProductToShopcart(@PathVariable UUID userID) {
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{userID}")
  ResponseEntity<?> updateProductInShopcart(@PathVariable UUID userID) {
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{userID}")
  ResponseEntity<?> deleteProductInShopcart(@PathVariable UUID userID) {
    return ResponseEntity.ok().build();
  }
}
