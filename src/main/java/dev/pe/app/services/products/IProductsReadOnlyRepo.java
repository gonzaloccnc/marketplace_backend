package dev.pe.app.services.products;

import dev.pe.app.services.IReadOnlyRepo;
import dev.pe.app.models.product.ProductsView;

import java.util.UUID;

public interface IProductsReadOnlyRepo extends IReadOnlyRepo<ProductsView, UUID> {
}
