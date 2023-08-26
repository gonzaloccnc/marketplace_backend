package dev.pe.app.v1.models.product;

import jakarta.persistence.*;
import lombok.Data;

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

  private String photoMd;
  private String photoLg;
}
