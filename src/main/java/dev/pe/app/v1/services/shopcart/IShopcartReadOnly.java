package dev.pe.app.v1.services.shopcart;

import dev.pe.app.v1.models.shopcart.ShopcartKey;
import dev.pe.app.v1.models.shopcart.ShopcartView;
import dev.pe.app.v1.services.IReadOnlyRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IShopcartReadOnly extends IReadOnlyRepo<ShopcartView, ShopcartKey> {
  @Query(value = "SELECT sp FROM vw_all_shopcart sp WHERE sp.id.idUserBuyer = ?1 AND sp.id.idProduct = ?2")
  Optional<ShopcartView> findByIdUserBuyerAndIdProduct(UUID id, UUID id_product);

  @Query(value = "SELECT sp FROM vw_all_shopcart sp WHERE sp.id.idUserBuyer = ?1")
  List<ShopcartView> findAllByIdUserBuyer(UUID id);


  @Query(value = "SELECT sp FROM vw_all_shopcart sp WHERE sp.id.idUserBuyer = ?1")
  Page<ShopcartView> findAllByIdUserBuyer(UUID id, Pageable pageable);
}
