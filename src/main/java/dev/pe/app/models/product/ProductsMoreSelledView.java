package dev.pe.app.models.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "vw_more_purchased")
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ProductsMoreSelledView {

  @Id
  private UUID idProduct;

  @JsonProperty("name")
  private String productName;

  private int stock;
  private double price;
  private String description;
  private boolean active;

  @JsonProperty("brand")
  private String brandName;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dateAdded;

  @JsonProperty("category")
  private String categoryName;

  @JsonProperty("subcategory")
  private String subcategoryName;

  private String photoMd;
  private String photoLg;
  private String seller;

  private long idCategory;
  private long idSubcategory;
  private long brandId;
  private UUID idUserSeller;
}
