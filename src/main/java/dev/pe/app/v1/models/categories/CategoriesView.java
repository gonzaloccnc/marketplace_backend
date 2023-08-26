package dev.pe.app.v1.models.categories;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "vw_order_categories")
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class CategoriesView {

  @Id
  private long idCategory;

  private String categoryName;
  private String catDes;
  private String subcategoryName;
  private String subDes;
  private Long idSubcategory;
}
