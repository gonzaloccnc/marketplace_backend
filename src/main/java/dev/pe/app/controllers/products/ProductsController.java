package dev.pe.app.controllers.products;

import dev.pe.app.domain.utils.responses.DataResponse;
import dev.pe.app.domain.utils.responses.ErrorInfo;
import dev.pe.app.domain.utils.responses.PageableResponse;
import dev.pe.app.models.Product;
import dev.pe.app.models.ProductsView;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController @RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {

  /// TODO separate the logic in methods for everyone of CRUD, not responses only logic

  private IProductsReadOnlyRepo productsReadOnlyRepo;
  private IProductRepo productRepo;

  @GetMapping
  public ResponseEntity<PageableResponse<ProductsView>> index(
      @PageableDefault(size = 20, sort = "productName")Pageable pageableQ,
      HttpServletRequest request
  ) {
    String PRODUCTS_URL = request.getRequestURI();
    var pageable = productsReadOnlyRepo.findAll(pageableQ);

    var domain = request.getRequestURL().toString().replace(PRODUCTS_URL, "") + PRODUCTS_URL;
    var prev = pageable.hasPrevious()
        ? domain + "?page=" + pageable.previousPageable().getPageNumber() + "&size=" + pageable.getSize()
        : null;

    var next = pageable.hasNext()
        ? domain + "?page=" + pageable.nextPageable().getPageNumber() + "&size=" + pageable.getSize()
        : null;

    return pageable.getNumber() >= pageable.getTotalPages()
        ?  ResponseEntity.badRequest().body(
            PageableResponse
                .<ProductsView>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("No content page, see that the page is less than " + pageable.getTotalPages())
                .build())
        : ResponseEntity.ok(
            PageableResponse
                .<ProductsView>builder()
                .data(pageable.getContent())
                .next(next)
                .prev(prev)
                .page(pageable.getNumber())
                .pageSize(pageable.getSize())
                .hints(pageable.getTotalElements())
                .totalPages(pageable.getTotalPages())
                .status(HttpStatus.OK.value())
                .error(null)
                .build());
  }

  @PostMapping
  public ResponseEntity<DataResponse<Product>> create(@RequestBody Product product) {
    var saveProduct = productRepo.save(product);

    return ResponseEntity.status(HttpStatus.CREATED).body(
        DataResponse
            .<Product>builder()
            .data(saveProduct)
            .status(HttpStatus.CREATED.value())
            .message("Product created successful")
            .build()
    );
  }

  @PatchMapping("/{id}")
  public ResponseEntity<DataResponse<Product>> update(
      @RequestBody Product product,
      @PathVariable UUID id
  ) {

    if(productRepo.findById(id).isEmpty()){
      return ResponseEntity.badRequest().body(
          DataResponse.<Product>builder()
              .data(null)
              .status(HttpStatus.BAD_REQUEST.value())
              .message("Product with id: " + id + " doesn't exist")
              .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
              .build()
      );
    }

    product.setIdProduct(id);
    var productUpdate = productRepo.save(product);

    return ResponseEntity.ok(
        DataResponse
            .<Product>builder()
            .data(productUpdate)
            .status(HttpStatus.OK.value())
            .message("Product update successful")
            .build()
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<DataResponse<Product>> delete(@PathVariable UUID id) {

    if(productRepo.findById(id).isEmpty()) {
      return ResponseEntity.ok(
          DataResponse
              .<Product>builder()
              .message("The product was deleted previously or doesn't exist")
              .status(HttpStatus.OK.value())
              .build()
      );
    }

    productRepo.deleteById(id);

    return ResponseEntity.ok(
        DataResponse
            .<Product>builder()
            .status(HttpStatus.OK.value())
            .message("Product deleted successful")
            .build()
    );
  }
}
