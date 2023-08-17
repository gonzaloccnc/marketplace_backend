package dev.pe.app.models.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Entity(name = "vw_sort_products")
@Immutable @Data
public class ProductsView {

  @Id @JsonProperty("id") @Column(name = "id_product")
  private UUID id;

  @JsonProperty("name")
  private String productName;
  private String description;
  private String brandName;
  private String categoryName;
  private String subcategoryName;
  private String seller;
  private int stock;
  private Double price;
  private boolean active;
}
