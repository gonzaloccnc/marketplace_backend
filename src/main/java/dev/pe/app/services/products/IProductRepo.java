package dev.pe.app.services.products;

import dev.pe.app.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductRepo extends JpaRepository<Product, UUID> {
}
