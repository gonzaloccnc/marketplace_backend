package dev.pe.app.controllers.products;

import dev.pe.app.models.ProductsView;

import java.util.UUID;

public interface IProductsReadOnlyRepo extends IReadOnlyRepo<ProductsView, UUID> {
}
