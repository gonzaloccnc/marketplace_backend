package dev.pe.app.v1.services.brands;

import dev.pe.app.v1.models.brands.BrandsView;
import dev.pe.app.v1.services.IReadOnlyRepo;

public interface IBrandRepoReadOnly extends IReadOnlyRepo<BrandsView, Long> {
}
