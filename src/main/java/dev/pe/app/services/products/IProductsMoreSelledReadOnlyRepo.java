package dev.pe.app.services.products;

import dev.pe.app.models.product.ProductsMoreSelledView;
import dev.pe.app.services.IReadOnlyRepo;

import java.util.UUID;

public interface IProductsMoreSelledReadOnlyRepo extends IReadOnlyRepo<ProductsMoreSelledView, UUID> {
}
