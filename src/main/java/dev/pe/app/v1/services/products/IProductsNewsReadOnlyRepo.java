package dev.pe.app.v1.services.products;

import dev.pe.app.v1.models.product.ProductsNewsView;
import dev.pe.app.v1.services.IReadOnlyRepo;

import java.util.UUID;

public interface IProductsNewsReadOnlyRepo extends IReadOnlyRepo<ProductsNewsView, UUID> {
}
