package dev.pe.app.v1.services.brands;

import dev.pe.app.v1.domain.utils.PageableUtil;
import dev.pe.app.v1.domain.utils.responses.DataResponseList;
import dev.pe.app.v1.domain.utils.responses.PageableResponse;
import dev.pe.app.v1.models.brands.BrandsView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class BrandService {

  private final IBrandRepoReadOnly brandRepoReadOnly;

  public DataResponseList<BrandsView> findAll() {
    var brands = brandRepoReadOnly.findAll();

    return DataResponseList
        .<BrandsView>builder()
        .data(brands)
        .status(HttpStatus.OK.value())
        .message("categories were listed")
        .build();
  }

  public PageableResponse<BrandsView> findAll(Pageable pageable) {
    var pageOfBrands = brandRepoReadOnly.findAll(pageable);
    var prev = PageableUtil.getPrevPage(pageOfBrands);
    var next = PageableUtil.getNextPage(pageOfBrands);

    if(pageOfBrands.getNumber() >= pageOfBrands.getTotalPages()) {
      return PageableResponse
          .<BrandsView>builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .error(
              "No content page, see that the page is less than " + pageOfBrands.getTotalPages()
          )
          .build();
    }

    return PageableResponse
        .<BrandsView>builder()
        .data(pageOfBrands.getContent())
        .prev(prev)
        .next(next)
        .page(pageOfBrands.getNumber())
        .pageSize(pageOfBrands.getSize())
        .hints(pageOfBrands.getTotalElements())
        .totalPages(pageOfBrands.getTotalPages())
        .status(HttpStatus.OK.value())
        .build();
  }
}
