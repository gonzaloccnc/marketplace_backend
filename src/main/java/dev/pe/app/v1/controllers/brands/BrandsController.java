package dev.pe.app.v1.controllers.brands;

import dev.pe.app.v1.domain.utils.PageableUtil;
import dev.pe.app.v1.domain.utils.responses.DataResponseList;
import dev.pe.app.v1.domain.utils.responses.PageableResponse;
import dev.pe.app.v1.models.brands.BrandsView;
import dev.pe.app.v1.services.brands.BrandService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandsController {

  private final BrandService brandService;

  @GetMapping
  ResponseEntity<DataResponseList<BrandsView>> getAll() {
    var message = brandService.findAll();

    return ResponseEntity.status(message.getStatus()).body(message);
  }

  @GetMapping("/page")
  ResponseEntity<PageableResponse<BrandsView>> getPageable(
      @PageableDefault(size = 20) Pageable pageable,
      HttpServletRequest req
  ) {

    var message = brandService.findAll(pageable);
    var map = PageableUtil.getLinks(message, req);

    message.setNext(map.get("next"));
    message.setPrev(map.get("prev"));

    return ResponseEntity.status(message.getStatus()).body(message);
  }
}
