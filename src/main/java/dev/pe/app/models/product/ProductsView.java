package dev.pe.app.models.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "vw_sort_products")
@Immutable @Data
public class ProductsView {

  @Id @JsonProperty("id") @Column(name = "id_product")
  private UUID id;

  @JsonProperty("name")
  private String productName;
  private String description;

  @JsonProperty("brand")
  private String brandName;

  @JsonProperty("category")
  private String categoryName;

  @JsonProperty("subcategory")
  private String subcategoryName;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dateAdded;

  private String seller;
  private int stock;
  private Double price;
  private boolean active;
  private String photoMd;
  private String photoLg;

  private long idCategory;
  private long idSubcategory;
  private long brandId;
  private UUID idUserSeller;
}
