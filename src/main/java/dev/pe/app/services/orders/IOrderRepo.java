package dev.pe.app.services.orders;

import dev.pe.app.models.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface IOrderRepo extends JpaRepository<Order, Long> {

  @Procedure(value = "sp_generate_purchase")
  void generatePurchase(@Param("id_user_buyer_") UUID id_user_buyer_);

  Page<Order> findAllByIdUserBuyer(Pageable pageable, UUID id);
}
