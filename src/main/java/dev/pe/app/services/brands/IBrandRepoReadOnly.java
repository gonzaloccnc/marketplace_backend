package dev.pe.app.services.brands;

import dev.pe.app.models.brands.BrandsView;
import dev.pe.app.services.IReadOnlyRepo;

public interface IBrandRepoReadOnly extends IReadOnlyRepo<BrandsView, Long> {
}
