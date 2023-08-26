package dev.pe.app.v1.services.products;

import dev.pe.app.v1.models.product.ProductsMoreSelledView;
import dev.pe.app.v1.services.IReadOnlyRepo;

import java.util.UUID;

public interface IProductsMoreSelledReadOnlyRepo extends IReadOnlyRepo<ProductsMoreSelledView, UUID> {
}
