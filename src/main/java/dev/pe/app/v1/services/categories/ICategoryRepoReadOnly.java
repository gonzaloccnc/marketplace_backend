package dev.pe.app.v1.services.categories;

import dev.pe.app.v1.models.categories.CategoriesView;
import dev.pe.app.v1.services.IReadOnlyRepo;

public interface ICategoryRepoReadOnly extends IReadOnlyRepo<CategoriesView, Long> {
}
