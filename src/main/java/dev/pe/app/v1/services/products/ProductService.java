package dev.pe.app.v1.services.products;

import dev.pe.app.v1.domain.utils.PageableUtil;
import dev.pe.app.v1.domain.utils.enums.Storage;
import dev.pe.app.v1.domain.utils.responses.DataResponse;
import dev.pe.app.v1.domain.utils.responses.PageableResponse;
import dev.pe.app.v1.models.product.Product;
import dev.pe.app.v1.models.product.ProductsMoreSelledView;
import dev.pe.app.v1.models.product.ProductsNewsView;
import dev.pe.app.v1.models.product.ProductsView;
import dev.pe.app.v1.services.ICrudService;
import dev.pe.app.v1.services.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService implements ICrudService<Product, ProductsView, UUID> {

  private final IProductRepo productRepo;
  private final IProductsReadOnlyRepo productsReadOnlyRepo;
  private final IProductsMoreSelledReadOnlyRepo productsMoreSelledReadOnlyRepo;
  private final IProductsNewsReadOnlyRepo productsNewsReadOnlyRepo;
  private final FileService fileService;

  @Override
  public DataResponse<Product> create(Product entity) {
    var saveProduct = productRepo.save(entity);

    return DataResponse
        .<Product>builder()
        .data(saveProduct)
        .status(HttpStatus.CREATED.value())
        .message("Product created successful")
        .build();

  }

  @Transactional
  public DataResponse<Product> create(Product entity, MultipartFile file) {
    if(file.isEmpty()) {
      return DataResponse
          .<Product>builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .message("File is empty, is required")
          .build();
    }

    var saveProduct = productRepo.save(entity);
    var filename = fileService.store(file, saveProduct.getIdProduct(), Storage.PRODUCTS);
    saveProduct.setPhotoMd("/" +filename);

    return DataResponse
        .<Product>builder()
        .data(saveProduct)
        .status(HttpStatus.CREATED.value())
        .message("Product created successful")
        .build();
  }

  @Override
  public PageableResponse<ProductsView> findAll(Pageable pageable) {
    var pageOfProducts = productsReadOnlyRepo.findAll(pageable);

    var prev = PageableUtil.getPrevPage(pageOfProducts);

    var next = PageableUtil.getNextPage(pageOfProducts);

    if(pageOfProducts.getNumber() >= pageOfProducts.getTotalPages() & pageOfProducts.getTotalPages() != 0) {
      return PageableResponse
          .<ProductsView>builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .error(
              "No content page, see that the page is less than " + pageOfProducts.getTotalPages()
          )
          .build();
    }

    return PageableResponse
        .<ProductsView>builder()
        .data(pageOfProducts.getContent())
        .next(next)
        .prev(prev)
        .page(pageOfProducts.getNumber())
        .pageSize(pageOfProducts.getSize())
        .hints(pageOfProducts.getTotalElements())
        .totalPages(pageOfProducts.getTotalPages())
        .status(HttpStatus.OK.value())
        .error(null)
        .build();
  }

  @Override
  public DataResponse<Product> update(Product entity, UUID id) {

    if(this.findById(id).getData() == null){
      return DataResponse
          .<Product>builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .message("Product with id: " + id + " doesn't exist")
          .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
          .build();
    }

    entity.setIdProduct(id);
    var productUpdate = productRepo.save(entity);

    return DataResponse
        .<Product>builder()
        .data(productUpdate)
        .status(HttpStatus.OK.value())
        .message("Product update successful")
        .build();
  }

  @Transactional
  public DataResponse<Product> update(Product entity, UUID id, @Nullable MultipartFile file) {
    var product = this.findById(id).getData();

    if(product == null) {
      return DataResponse
          .<Product>builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .message("Product with id: " + id + " doesn't exist")
          .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
          .build();
    }

    entity.setIdProduct(id);
    var productUpdate = productRepo.save(entity);

    if(file == null || file.isEmpty()) {
      var fileName = fileService.load(product.getPhotoMd(), Storage.PRODUCTS).getFileName().toString();
      productUpdate.setPhotoMd("/" + fileName);
      return DataResponse
          .<Product>builder()
          .data(productUpdate)
          .status(HttpStatus.OK.value())
          .message("Product update successful")
          .build();
    }

    var filename = fileService.store(file, productUpdate.getIdProduct(), Storage.PRODUCTS);
    productUpdate.setPhotoMd("/" + filename);

    return DataResponse
        .<Product>builder()
        .data(productUpdate)
        .status(HttpStatus.OK.value())
        .message("Product update successful")
        .build();
  }

  @Override
  public DataResponse<Product> delete(UUID id) {

    if(this.findById(id).getData() == null) {
      return DataResponse
          .<Product>builder()
          .message("The product with id: " + id + " was deleted previously or doesn't exist")
          .status(HttpStatus.BAD_REQUEST.value())
          .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
          .build();
    }

    productRepo.deleteById(id);

    return DataResponse
        .<Product>builder()
        .status(HttpStatus.OK.value())
        .message("Product deleted successful")
        .build();
  }

  @Override
  public DataResponse<ProductsView> findById(UUID id) {

    var product = productsReadOnlyRepo.findById(id).orElse(null);

    if(product == null) {
      return DataResponse
          .<ProductsView>builder()
          .status(400)
          .message("product with id: " + id + " doesn't exist try other id type UUID")
          .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
          .build();
    }

    return DataResponse
        .<ProductsView>builder()
        .data(product)
        .status(HttpStatus.OK.value())
        .message("product found successful")
        .build();
  }

  public PageableResponse<ProductsMoreSelledView> findAllMoreSelled(Pageable pageable) {
    var pageOfProducts = productsMoreSelledReadOnlyRepo.findAll(pageable);

    var prev = PageableUtil.getPrevPage(pageOfProducts);

    var next = PageableUtil.getNextPage(pageOfProducts);

    if(pageOfProducts.getNumber() >= pageOfProducts.getTotalPages() & pageOfProducts.getTotalPages() != 0) {
      return PageableResponse
          .<ProductsMoreSelledView>builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .error(
              "No content page, see that the page is less than " + pageOfProducts.getTotalPages()
          )
          .build();
    }

    return PageableResponse
        .<ProductsMoreSelledView>builder()
        .data(pageOfProducts.getContent())
        .next(next)
        .prev(prev)
        .page(pageOfProducts.getNumber())
        .pageSize(pageOfProducts.getSize())
        .hints(pageOfProducts.getTotalElements())
        .totalPages(pageOfProducts.getTotalPages())
        .status(HttpStatus.OK.value())
        .error(null)
        .build();
  }

  public PageableResponse<ProductsNewsView> findAllNews(Pageable pageable) {
    var pageOfProducts = productsNewsReadOnlyRepo.findAll(pageable);

    var prev = PageableUtil.getPrevPage(pageOfProducts);

    var next = PageableUtil.getNextPage(pageOfProducts);

    if(pageOfProducts.getNumber() >= pageOfProducts.getTotalPages() & pageOfProducts.getTotalPages() != 0) {
      return PageableResponse
          .<ProductsNewsView>builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .error(
              "No content page, see that the page is less than " + pageOfProducts.getTotalPages()
          )
          .build();
    }

    return PageableResponse
        .<ProductsNewsView>builder()
        .data(pageOfProducts.getContent())
        .next(next)
        .prev(prev)
        .page(pageOfProducts.getNumber())
        .pageSize(pageOfProducts.getSize())
        .hints(pageOfProducts.getTotalElements())
        .totalPages(pageOfProducts.getTotalPages())
        .status(HttpStatus.OK.value())
        .error(null)
        .build();
  }
}
