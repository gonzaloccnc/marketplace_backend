package dev.pe.app.v1.models.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
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

  @Enumerated(EnumType.STRING)
  private Delivery stateOrder;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dateAdded;
  private short quantity;


  public enum Delivery {
    PENDING("Waiting for processing"),
    RECEIVED("Order has received"),
    ON_ROUTE("Order is on way"),
    CANCELED("Order has been cancelled"),
    RETURNED("Order has been returned"),
    OUT_OF_STOCK("Products is currently out of stock"),
    OUT_OF_TIME("Delivery time exceeded");

    private final String value;
    Delivery(String value){
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }
  }
}
