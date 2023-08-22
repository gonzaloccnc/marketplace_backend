package dev.pe.app.controllers.orders;

import dev.pe.app.services.orders.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController @RequestMapping("/order") @RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping("/{idUser}")
  public ResponseEntity<?> getOrder(@PathVariable UUID idUser) {
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{idUser}")
  public ResponseEntity<?> createOrder(@PathVariable UUID idUser) {
    orderService.create(idUser);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{idUser}")
  public ResponseEntity<?> updateOrder(@PathVariable UUID idUser) {
    return ResponseEntity.ok().build();
  }
}
