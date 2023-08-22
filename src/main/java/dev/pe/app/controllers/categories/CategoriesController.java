package dev.pe.app.controllers.categories;

import dev.pe.app.domain.utils.PageableUtil;
import dev.pe.app.domain.utils.responses.DataResponseList;
import dev.pe.app.domain.utils.responses.PageableResponse;
import dev.pe.app.models.categories.CategoriesView;
import dev.pe.app.services.categories.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {

  private final CategoryService categoryService;

  @GetMapping
  ResponseEntity<DataResponseList<CategoriesView>> getAll() {
    var message = categoryService.findAll();

    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @GetMapping("/page")
  ResponseEntity<PageableResponse<CategoriesView>> getPageable(
      @PageableDefault(size = 20) Pageable pageable,
      HttpServletRequest req
  ) {

    var message = categoryService.findAll(pageable);
    var map = PageableUtil.getLinks(message, req);

    message.setNext(map.get("next"));
    message.setPrev(map.get("prev"));

    return ResponseEntity.status(message.getStatus()).body(message);
  }
}
