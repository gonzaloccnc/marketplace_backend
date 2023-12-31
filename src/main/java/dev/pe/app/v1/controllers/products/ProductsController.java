package dev.pe.app.v1.controllers.products;

import dev.pe.app.v1.domain.utils.PageableUtil;
import dev.pe.app.v1.domain.utils.responses.DataResponse;
import dev.pe.app.v1.domain.utils.responses.PageableResponse;
import dev.pe.app.v1.models.product.Product;
import dev.pe.app.v1.models.product.ProductsView;
import dev.pe.app.v1.services.products.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
  public ResponseEntity<DataResponse<Product>> create(
      @RequestPart("file") MultipartFile file,
      @RequestPart("product") Product product,
      HttpServletRequest req
  ) {
    var message = productService.create(product, file);

    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<DataResponse<Product>> updateWithFile(
      @PathVariable UUID id,
      @Nullable @RequestPart("file") MultipartFile file,
      @RequestPart("product") Product product,
      HttpServletRequest req
  ){
    var message = productService.update(product, id, file);

    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<DataResponse<Product>> delete(@PathVariable UUID id) {
    var message = productService.delete(id);
    return ResponseEntity.status(message.getStatus()).body(message);
  }
}
