package dev.pe.app.v1.services.products;

import dev.pe.app.v1.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductRepo extends JpaRepository<Product, UUID> {
}
