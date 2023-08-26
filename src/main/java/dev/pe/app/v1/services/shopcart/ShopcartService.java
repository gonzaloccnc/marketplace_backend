package dev.pe.app.v1.services.shopcart;

import dev.pe.app.v1.controllers.auth.IUserRepo;
import dev.pe.app.v1.domain.utils.PageableUtil;
import dev.pe.app.v1.domain.utils.responses.DataResponse;
import dev.pe.app.v1.domain.utils.responses.DataResponseList;
import dev.pe.app.v1.domain.utils.responses.PageableResponse;
import dev.pe.app.v1.domain.dto.ShopcartViewDTO;
import dev.pe.app.v1.models.shopcart.Shopcart;
import dev.pe.app.v1.models.shopcart.ShopcartKey;
import dev.pe.app.v1.models.shopcart.ShopcartView;
import dev.pe.app.v1.services.ICrudService;
import dev.pe.app.v1.services.products.IProductsReadOnlyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class ShopcartService implements ICrudService<Shopcart, ShopcartViewDTO, ShopcartKey> {

  private final IShopcartRepo shopcartRepo;
  private final IShopcartReadOnly shopcartReadOnly;
  private final IProductsReadOnlyRepo productsReadOnlyRepo;
  private final IUserRepo userRepo;

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

  public DataResponseList<ShopcartViewDTO> findAll(UUID id) {
    var user = userRepo.findById(id).orElse(null);
    var shopcart = shopcartReadOnly.findAllByIdUserBuyer(id);
    var dtoList = this.mapToDTO(shopcart);

    if (user == null) {
      return DataResponseList
          .<ShopcartViewDTO>builder()
          .message("user with id: " + id + " doesn't exist")
          .status(HttpStatus.BAD_REQUEST.value())
          .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
          .build();
    }

    return DataResponseList
        .<ShopcartViewDTO>builder()
        .data(dtoList)
        .message("shopcart was listed")
        .status(HttpStatus.OK.value())
        .build();
  }

  public PageableResponse<ShopcartViewDTO> findAll(Pageable pageable, UUID id) {
    var user = userRepo.findById(id).orElse(null);
    var shopcart = shopcartReadOnly.findAllByIdUserBuyer(id, pageable);
    var dtoList = this.mapToDTO(shopcart.getContent());

    if (user == null) {
      return PageableResponse
          .<ShopcartViewDTO>builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .error("user with id: " + id + " doesn't exist")
          .build();
    }

    var prev = PageableUtil.getPrevPage(shopcart);
    var next = PageableUtil.getNextPage(shopcart);

    if(shopcart.getNumber() >= shopcart.getTotalPages() & shopcart.getTotalPages() != 0) {
      return PageableResponse
          .<ShopcartViewDTO>builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .error(
              "No content page, see that the page is less than " + shopcart.getTotalPages()
          )
          .build();
    }

    return PageableResponse
        .<ShopcartViewDTO>builder()
        .data(dtoList)
        .prev(prev)
        .next(next)
        .page(shopcart.getNumber())
        .pageSize(shopcart.getSize())
        .hints(shopcart.getTotalElements())
        .totalPages(shopcart.getTotalPages())
        .status(HttpStatus.OK.value())
        .build();
  }

  private List<ShopcartViewDTO> mapToDTO(List<ShopcartView> view) {
   return view.stream().map(x -> ShopcartViewDTO.builder()
       .idUserBuyer(x.getId().getIdUserBuyer())
       .idProduct(x.getId().getIdProduct())
       .productName(x.getProductName())
       .price(x.getPrice())
       .quantity(x.getQuantity())
       .total(x.getTotal())
       .dateAdded(x.getDateAdded())
       .photoMd(x.getPhotoMd())
       .photoLg(x.getPhotoLg())
       .build()
   ).toList();
  }

}
