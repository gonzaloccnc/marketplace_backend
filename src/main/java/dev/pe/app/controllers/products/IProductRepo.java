package dev.pe.app.controllers.products;

import dev.pe.app.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductRepo extends JpaRepository<Product, UUID> {
}
