package dev.pe.app.models.brands;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "vw_all_brands")
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class BrandsView {

  @Id @JsonProperty("id")
  private long brandId;

  @JsonProperty("name")
  private String brandName;
}
