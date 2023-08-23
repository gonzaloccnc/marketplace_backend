package dev.pe.app.controllers.orders;

import dev.pe.app.domain.utils.PageableUtil;
import dev.pe.app.domain.utils.responses.DataResponse;
import dev.pe.app.domain.utils.responses.PageableResponse;
import dev.pe.app.models.order.Order;
import dev.pe.app.services.orders.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController @RequestMapping("/order") @RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping("/{idUser}")
  public ResponseEntity<PageableResponse<Order>> getOrder(
      @PageableDefault(size = 15) Pageable pageable,
      @PathVariable UUID idUser,
      HttpServletRequest req
  ) {
    var message = orderService.getOrders(pageable, idUser);

    var map = PageableUtil.getLinks(message, req);

    message.setPrev(map.get("prev"));
    message.setNext(map.get("next"));

    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @PostMapping("/{idUser}")
  public ResponseEntity<DataResponse<Order>> createOrder(@PathVariable UUID idUser) {

    var message = orderService.create(idUser);

    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @PatchMapping("/{idUser}")
  public ResponseEntity<?> updateOrder(@PathVariable UUID idUser) {
    return ResponseEntity.ok().build();
  }
}
