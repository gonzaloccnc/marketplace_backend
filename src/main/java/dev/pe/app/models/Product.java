package dev.pe.app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
