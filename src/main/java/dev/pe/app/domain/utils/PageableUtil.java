package dev.pe.app.domain.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public class PageableUtil {

  public static String getPrevPage(Page page) {
    return page.hasPrevious()
        ? "?page=" + page.previousPageable().getPageNumber() + "&size=" + page.getSize()
        : null;
  }

  public static String getNextPage(Page page) {
    return page.hasNext()
        ? "?page=" + page.nextPageable().getPageNumber() + "&size=" + page.getSize()
        : null;
  }

  public static String getDomain(HttpServletRequest request) {
    String URI = request.getRequestURI();

    return request.getRequestURL().toString().replace(URI, "") + URI;
  }
}
