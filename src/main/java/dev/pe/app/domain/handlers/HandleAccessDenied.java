package dev.pe.app.domain.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HandleAccessDenied implements AccessDeniedHandler {
  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException
  ) throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied, check your token or expiration token");
  }
}
