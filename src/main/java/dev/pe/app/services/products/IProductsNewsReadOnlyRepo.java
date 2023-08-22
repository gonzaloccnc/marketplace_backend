package dev.pe.app.services.products;

import dev.pe.app.models.product.ProductsNewsView;
import dev.pe.app.services.IReadOnlyRepo;

import java.util.UUID;

public interface IProductsNewsReadOnlyRepo extends IReadOnlyRepo<ProductsNewsView, UUID> {
}
