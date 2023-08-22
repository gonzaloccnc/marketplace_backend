package dev.pe.app.controllers.products;

import dev.pe.app.domain.utils.PageableUtil;
import dev.pe.app.domain.utils.responses.DataResponse;
import dev.pe.app.domain.utils.responses.PageableResponse;
import dev.pe.app.models.product.Product;
import dev.pe.app.models.product.ProductsView;
import dev.pe.app.services.products.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController @RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {

  private ProductService productService;

  @GetMapping
  public ResponseEntity<PageableResponse<ProductsView>> index(
      @PageableDefault(size = 20, sort = "productName") Pageable pageable,
      HttpServletRequest request
  ) {
    var message = productService.findAll(pageable);

    var map = PageableUtil.getLinks(message, request);

    message.setPrev(map.get("prev"));
    message.setNext(map.get("next"));

    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @GetMapping("/new")
  public ResponseEntity<PageableResponse<?>> newProducts(
    @PageableDefault(size = 20, sort = "productName") Pageable pageable,
    HttpServletRequest req
  ) {
    var message = productService.findAllNews(pageable);

    var map = PageableUtil.getLinks(message, req);

    message.setPrev(map.get("prev"));
    message.setNext(map.get("next"));

    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @GetMapping("/best/selled")
  public ResponseEntity<PageableResponse<?>> bestSells(
      @PageableDefault(size = 20, sort = "productName") Pageable pageable,
      HttpServletRequest req
  ) {
    var message = productService.findAllMoreSelled(pageable);

    var map = PageableUtil.getLinks(message, req);

    message.setPrev(map.get("prev"));
    message.setNext(map.get("next"));

    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DataResponse<ProductsView>> getById(@PathVariable UUID id) {
    var message = productService.findById(id);
    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @PostMapping
  public ResponseEntity<DataResponse<Product>> create(@RequestBody Product product) {
    var message = productService.create(product);
    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<DataResponse<Product>> update(
      @RequestBody Product product,
      @PathVariable UUID id
  ) {
    var message = productService.update(product, id);
    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<DataResponse<Product>> delete(@PathVariable UUID id) {
    var message = productService.delete(id);
    return ResponseEntity.status(message.getStatus()).body(message);
  }
}
