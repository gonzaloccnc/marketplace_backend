package dev.pe.app.v1.services;

import dev.pe.app.v1.domain.utils.responses.DataResponse;
import dev.pe.app.v1.domain.utils.responses.DataResponseList;
import dev.pe.app.v1.domain.utils.responses.PageableResponse;
import org.springframework.data.domain.Pageable;

public interface ICrudService<T, TV, K> {

  DataResponse<T> create(T entity);
  default DataResponseList<TV> findAll() { return null; };
  default PageableResponse<TV> findAll(Pageable pageable) { return null; };
  DataResponse<T> update(T entity, K id);
  DataResponse<T> delete(K id);
  default DataResponse<TV> findById(K id) { return null; };
}
