package dev.pe.app.models.product;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data @Entity(name = "products")
public class Product {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
  private UUID idProduct;

  private String productName;
  private int stock;
  private double price;
  private String description;
  private boolean active;
  private UUID idUser;
  private int idSubcategory;
  private int brandsId;

  @Transient
  private MultipartFile file;
}
