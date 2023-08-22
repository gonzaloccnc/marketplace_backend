package dev.pe.app.services.shopcart;

import dev.pe.app.models.shopcart.ShopcartView;
import dev.pe.app.services.IReadOnlyRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IShopcartReadOnly extends IReadOnlyRepo<ShopcartView, UUID> {
  Optional<ShopcartView> findByIdUserBuyerAndIdProduct(UUID id, UUID id_product);

  List<ShopcartView> findAllByIdUserBuyer(UUID id);

  Page<ShopcartView> findAllByIdUserBuyer(Pageable pageable, UUID id);

  Optional<ShopcartView> findByIdUserBuyer(UUID id);
}
