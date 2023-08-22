package dev.pe.app.services.categories;

import dev.pe.app.domain.utils.PageableUtil;
import dev.pe.app.domain.utils.responses.DataResponseList;
import dev.pe.app.domain.utils.responses.PageableResponse;
import dev.pe.app.models.categories.CategoriesView;
import dev.pe.app.models.product.ProductsView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class CategoryService {

  private final ICategoryRepoReadOnly categoryRepoReadOnly;

  public DataResponseList<CategoriesView> findAll() {
    var categories = categoryRepoReadOnly.findAll();

    return DataResponseList
        .<CategoriesView>builder()
        .data(categories)
        .status(HttpStatus.OK.value())
        .message("categories were listed")
        .build();
  }

  public PageableResponse<CategoriesView> findAll(Pageable pageable) {
    var pageOfCategories = categoryRepoReadOnly.findAll(pageable);
    var prev = PageableUtil.getPrevPage(pageOfCategories);
    var next = PageableUtil.getNextPage(pageOfCategories);

    if(pageOfCategories.getNumber() >= pageOfCategories.getTotalPages()) {
      return PageableResponse
          .<CategoriesView>builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .error(
              "No content page, see that the page is less than " + pageOfCategories.getTotalPages()
          )
          .build();
    }

    return PageableResponse
        .<CategoriesView>builder()
        .data(pageOfCategories.getContent())
        .prev(prev)
        .next(next)
        .page(pageOfCategories.getNumber())
        .pageSize(pageOfCategories.getSize())
        .hints(pageOfCategories.getTotalElements())
        .totalPages(pageOfCategories.getTotalPages())
        .status(HttpStatus.OK.value())
        .build();
  }
}
