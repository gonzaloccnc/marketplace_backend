package dev.pe.app.controllers.shopcart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController @RequestMapping("/shopcart")
public class ShopCartController {

  @GetMapping("/{userID}")
  ResponseEntity<?> getShopcart(@PathVariable UUID userID) {
    return ResponseEntity.ok().build();
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
