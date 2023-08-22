package dev.pe.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface IReadOnlyRepo<T, ID> extends Repository<T, ID> {
  List<T> findAll();

  List<T> findAll(Sort sort);

  Page<T> findAll(Pageable pageable);
}
