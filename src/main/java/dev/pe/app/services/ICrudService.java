package dev.pe.app.services;

import dev.pe.app.domain.utils.responses.DataResponse;
import dev.pe.app.domain.utils.responses.DataResponseList;
import dev.pe.app.domain.utils.responses.PageableResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ICrudService<T, TV, K> {

  DataResponse<T> create(T entity);
  default DataResponseList<TV> findAll() { return null; };
  default PageableResponse<TV> findAll(Pageable pageable) { return null; };
  DataResponse<T> update(T entity, K id);
  DataResponse<T> delete(K id);
  default DataResponse<TV> findById(K id) { return null; };
}
