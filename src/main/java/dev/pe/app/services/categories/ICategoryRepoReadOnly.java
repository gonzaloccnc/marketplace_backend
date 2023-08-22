package dev.pe.app.services.categories;

import dev.pe.app.models.categories.CategoriesView;
import dev.pe.app.services.IReadOnlyRepo;

public interface ICategoryRepoReadOnly extends IReadOnlyRepo<CategoriesView, Long> {
}
