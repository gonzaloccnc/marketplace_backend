package dev.pe.app.services.shopcart;

import dev.pe.app.domain.utils.PageableUtil;
import dev.pe.app.domain.utils.responses.DataResponse;
import dev.pe.app.domain.utils.responses.DataResponseList;
import dev.pe.app.domain.utils.responses.PageableResponse;
import dev.pe.app.models.shopcart.Shopcart;
import dev.pe.app.models.shopcart.ShopcartKey;
import dev.pe.app.models.shopcart.ShopcartView;
import dev.pe.app.services.ICrudService;
import dev.pe.app.services.products.IProductsReadOnlyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class ShopcartService implements ICrudService<Shopcart, ShopcartView, ShopcartKey> {

  private final IShopcartRepo shopcartRepo;
  private final IShopcartReadOnly shopcartReadOnly;
  private final IProductsReadOnlyRepo productsReadOnlyRepo;

  @Override
  public DataResponse<Shopcart> create(Shopcart entity) {
    var isSameUser = productsReadOnlyRepo.findById(entity.getId().getIdUserBuyer()).isPresent();

    if (isSameUser) {
      return DataResponse
          .<Shopcart>builder()
          .message("self-purchases are not allowed")
          .status(HttpStatus.BAD_REQUEST.value())
          .build();
    }

    var shopcart = shopcartRepo.save(entity);

    return DataResponse
        .<Shopcart>builder()
        .data(shopcart)
        .message("product was added successful")
        .status(HttpStatus.CREATED.value())
        .build();
  }

  @Override
  public DataResponse<Shopcart> update(Shopcart entity, ShopcartKey id) {

    var existProduct = shopcartReadOnly
        .findByIdUserBuyerAndIdProduct(id.getIdUserBuyer(), id.getIdProduct())
        .isPresent();

    if (!existProduct) {
      return DataResponse
          .<Shopcart>builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .message(
              "product with value: " + id.getIdProduct() + " and user with id: "
                  + id.getIdUserBuyer() + " doesn't exist"
          )
          .build();
    }

    var shopcartUpdate = shopcartRepo.save(entity);
    return DataResponse
        .<Shopcart>builder()
        .data(shopcartUpdate)
        .status(HttpStatus.OK.value())
        .message("shopcart update successful")
        .build();
  }

  @Override
  public DataResponse<Shopcart> delete(ShopcartKey id) {
    var existProduct = shopcartReadOnly
        .findByIdUserBuyerAndIdProduct(id.getIdUserBuyer(), id.getIdProduct())
        .isPresent();

    if (!existProduct) {
      return DataResponse
          .<Shopcart>builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .message(
              "product with value: " + id.getIdProduct() + " and user id: "
              + id.getIdUserBuyer() + " doesn't exist or was previously removed"
          )
          .build();
    }

    shopcartRepo.deleteById(id);
    return DataResponse
        .<Shopcart>builder()
        .status(HttpStatus.OK.value())
        .message("shopcart delete successful")
        .build();
  }

  public DataResponseList<Shopcart> createAll(List<Shopcart> entities) {
    var shopcartList = shopcartRepo.saveAll(entities);

    return DataResponseList
        .<Shopcart>builder()
        .data(shopcartList)
        .status(HttpStatus.CREATED.value())
        .message("product list was added")
        .build();
  }

  public DataResponseList<ShopcartView> findAll(UUID id) {
    var shopcart = shopcartReadOnly.findAllByIdUserBuyer(id);

    return DataResponseList
        .<ShopcartView>builder()
        .data(shopcart)
        .message("shopcart was listed")
        .status(HttpStatus.OK.value())
        .build();
  }

  public PageableResponse<ShopcartView> findAll(Pageable pageable, UUID id) {
    var shopcart = shopcartReadOnly.findAllByIdUserBuyer(pageable, id);

    var prev = PageableUtil.getPrevPage(shopcart);
    var next = PageableUtil.getNextPage(shopcart);

    return PageableResponse
        .<ShopcartView>builder()
        .data(shopcart.getContent())
        .prev(prev)
        .next(next)
        .page(shopcart.getNumber())
        .pageSize(shopcart.getSize())
        .hints(shopcart.getTotalElements())
        .totalPages(shopcart.getTotalPages())
        .status(HttpStatus.OK.value())
        .build();
  }
}
