package dev.pe.app.services.shopcart;

import dev.pe.app.domain.utils.responses.DataResponse;
import dev.pe.app.domain.utils.responses.DataResponseList;
import dev.pe.app.domain.utils.responses.PageableResponse;
import dev.pe.app.models.shopcart.Shopcart;
import dev.pe.app.models.shopcart.ShopcartKey;
import dev.pe.app.models.shopcart.ShopcartView;
import dev.pe.app.services.ICrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service @RequiredArgsConstructor
public class ShopcartService implements ICrudService<Shopcart, ShopcartView, ShopcartKey> {

  private final IShopcartRepo shopcartRepo;
  private final IShopcartReadOnly shopcartReadOnly;

  @Override
  public DataResponse<Shopcart> create(Shopcart entity) {
    var shopcart = shopcartRepo.save(entity);
    return DataResponse
        .<Shopcart>builder()
        .data(shopcart)
        .status(HttpStatus.CREATED.value())
        .message("shopcart added successful")
        .build();
  }

  @Override
  public DataResponseList<ShopcartView> findAll(UUID id) {
    var shopcart = shopcartReadOnly.findAllById(id);

    return DataResponseList
        .<ShopcartView>builder()
        .data(shopcart)
        .message("products was listed")
        .status(HttpStatus.OK.value())
        .build();
  }

  @Override
  public PageableResponse<ShopcartView> findAll(Pageable pageable, UUID id) {
    var shopcart = shopcartReadOnly.findAllById(pageable, id);
    return PageableResponse
        .<ShopcartView>builder()
        .data(shopcart.getContent())
        .prev("")
        .next("")
        .hints(1L)
        .totalPages(1)
        .page(1)
        .pageSize(1)
        .status(HttpStatus.OK.value())
        .build();
  }

  @Override
  public DataResponse<Shopcart> update(Shopcart entity, ShopcartKey id) {
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

    shopcartRepo.deleteById(id);
    return DataResponse
        .<Shopcart>builder()
        .status(HttpStatus.OK.value())
        .message("shopcart delete successful")
        .build();
  }
}
