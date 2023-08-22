package dev.pe.app.models.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "purchase_order")
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Order {

  @Id
  private Long idOrder;

  private UUID idUserSeller;
  private UUID idUserBuyer;
  private UUID idProduct;

  @Enumerated
  private Delivery stateOrder;

  private LocalDate dateAdded;
  private short quantity;

  public enum Delivery {

    ON_HOLD("EN ESPERA"),
    RECEIVED("RECIBIDO"),
    ON_THE_WAY("EN CAMINO"),
    DELIVERED("ENTREGADO"),
    CANCELLED("CANCELADO"),
    RETURNED("DEVUELTO"),
    NO_STOCK("SIN STOCK"),
    TIMEOUT("EXPIRADO");

    Delivery(String value) {
    }

  }
}
