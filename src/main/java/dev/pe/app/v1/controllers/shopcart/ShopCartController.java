package dev.pe.app.v1.controllers.shopcart;

import dev.pe.app.v1.domain.utils.PageableUtil;
import dev.pe.app.v1.domain.utils.request.shopcart.ShopcartListReq;
import dev.pe.app.v1.domain.utils.request.shopcart.ShopcartReq;
import dev.pe.app.v1.domain.utils.responses.DataResponse;
import dev.pe.app.v1.domain.utils.responses.DataResponseList;
import dev.pe.app.v1.domain.utils.responses.PageableResponse;
import dev.pe.app.v1.domain.dto.ShopcartViewDTO;
import dev.pe.app.v1.models.shopcart.Shopcart;
import dev.pe.app.v1.models.shopcart.ShopcartKey;
import dev.pe.app.v1.services.shopcart.ShopcartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController @RequestMapping("/shopcart") @RequiredArgsConstructor
public class ShopCartController {

  private final ShopcartService shopcartService;

  @GetMapping("/page/{userID}")
  ResponseEntity<PageableResponse<ShopcartViewDTO>> index(
      @PageableDefault Pageable pageable,
      @PathVariable UUID userID,
      HttpServletRequest request
  ) {

    var message = shopcartService.findAll(pageable, userID);
    var map = PageableUtil.getLinks(message, request);

    message.setPrev(map.get("prev"));
    message.setNext(map.get("next"));

    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @GetMapping("/{userID}")
  ResponseEntity<DataResponseList<ShopcartViewDTO>> getShopcart(@PathVariable UUID userID) {
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
