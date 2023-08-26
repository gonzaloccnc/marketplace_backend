package dev.pe.app.v1.services.products;

import dev.pe.app.v1.services.IReadOnlyRepo;
import dev.pe.app.v1.models.product.ProductsView;

import java.util.Optional;
import java.util.UUID;

public interface IProductsReadOnlyRepo extends IReadOnlyRepo<ProductsView, UUID> {
  Optional<ProductsView> findById(UUID id);
}
