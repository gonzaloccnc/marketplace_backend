package dev.pe.app.v1.domain.utils;

import dev.pe.app.v1.domain.utils.enums.Storage;
import dev.pe.app.v1.domain.utils.responses.PageableResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class PageableUtil {

  public static String getPrevPage(Page<?> page) {
    return page.hasPrevious()
        ? "?page=" + page.previousPageable().getPageNumber() + "&size=" + page.getSize()
        : null;
  }

  public static String getNextPage(Page<?> page) {
    return page.hasNext()
        ? "?page=" + page.nextPageable().getPageNumber() + "&size=" + page.getSize()
        : null;
  }

  public static Map<String, String> getLinks(PageableResponse<?> response, HttpServletRequest request) {
    var map = new HashMap<String, String>();

    if(response.getData() != null) {
      var next = response.getNext() == null ? null : getDomain(request) + response.getNext();
      var prev = response.getPrev() == null ? null : getDomain(request) + response.getPrev();

      map.put("next", next);
      map.put("prev", prev);
    }

    return map;
  }

  public static String getDomain(HttpServletRequest request) {
    return request.getRequestURL().toString();
  }

  public static String getImageDomain(HttpServletRequest request, Storage storage) {
    String URI = request.getRequestURI();

    return request
        .getRequestURL()
        .toString()
        .replace(URI, "/api/v1/images/" + storage.name().toLowerCase());
  }
}
