package dev.pe.app.controllers.shopcart;

import dev.pe.app.domain.utils.PageableUtil;
import dev.pe.app.domain.utils.request.shopcart.ShopcartListReq;
import dev.pe.app.domain.utils.request.shopcart.ShopcartReq;
import dev.pe.app.domain.utils.responses.DataResponse;
import dev.pe.app.domain.utils.responses.DataResponseList;
import dev.pe.app.domain.utils.responses.PageableResponse;
import dev.pe.app.models.shopcart.Shopcart;
import dev.pe.app.models.shopcart.ShopcartKey;
import dev.pe.app.models.shopcart.ShopcartView;
import dev.pe.app.services.shopcart.ShopcartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController @RequestMapping("/shopcart") @RequiredArgsConstructor
public class ShopCartController {

  private final ShopcartService shopcartService;

  @GetMapping("/page/{userID}")
  ResponseEntity<PageableResponse<ShopcartView>> index(
      @PageableDefault(size = 20) Pageable pageable,
      @PathVariable UUID userID,
      HttpServletRequest request
  ) {

    var domain = PageableUtil.getDomain(request);
    var message = shopcartService.findAll(pageable, userID);

    if (message.getData() != null) {
      message.setNext(message.getNext() == null ? null : domain + message.getNext());
      message.setPrev(message.getPrev() == null ? null : domain + message.getPrev());
    }

    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @GetMapping("/{userID}")
  ResponseEntity<DataResponseList<ShopcartView>> getShopcart(@PathVariable UUID userID) {
    // TODO BUG AL DEVOLVER EL CARRITO DE COMPRAS DEVUELVE 2 IGUALES LO CUAL ES INCORRECTO
    var message = shopcartService.findAll(userID);
    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @PostMapping()
  ResponseEntity<DataResponse<Shopcart>> addProductToShopcart(@RequestBody ShopcartReq shopcartReq) {
    var key = ShopcartKey.builder()
        .idProduct(shopcartReq.getIdProduct())
        .idUserBuyer(shopcartReq.getIdUserBuyer())
        .build();

    var shopcart = Shopcart
        .builder()
        .id(key)
        .quantity(shopcartReq.getQuantity())
        .build();

    var message = shopcartService.create(shopcart);
    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @PostMapping("/many/{idUser}")
  ResponseEntity<DataResponseList<Shopcart>> addProductsToShopcart(
      @PathVariable UUID idUser,
      @RequestBody ShopcartListReq listReq
  ) {
    var shopcartList = new ArrayList<Shopcart>();

    listReq.getProducts().forEach(pd -> {
      var key = ShopcartKey.builder()
          .idProduct(pd.getIdProduct())
          .idUserBuyer(idUser)
          .build();

      var shopcart = Shopcart
          .builder()
          .id(key)
          .quantity(pd.getQuantity())
          .build();

      shopcartList.add(shopcart);
    });

    var message = shopcartService.createAll(shopcartList);

    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @PatchMapping()
  ResponseEntity<DataResponse<Shopcart>> updateProductInShopcart(
      @RequestBody ShopcartReq shopcartReq
  ) {

    var key = ShopcartKey.builder()
        .idProduct(shopcartReq.getIdProduct())
        .idUserBuyer(shopcartReq.getIdUserBuyer())
        .build();

    var shopcart = Shopcart
        .builder()
        .id(key)
        .quantity(shopcartReq.getQuantity())
        .build();

    var message = shopcartService.update(shopcart, key);
    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @DeleteMapping()
  ResponseEntity<DataResponse<Shopcart>> deleteProductInShopcart(@RequestBody ShopcartKey key) {
    var message = shopcartService.delete(key);
    return ResponseEntity.status(message.getStatus()).body(message);
  }

}
