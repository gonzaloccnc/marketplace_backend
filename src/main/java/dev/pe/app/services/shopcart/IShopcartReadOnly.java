package dev.pe.app.services.shopcart;

import dev.pe.app.models.shopcart.ShopcartView;
import dev.pe.app.services.IReadOnlyRepo;

import java.util.UUID;

public interface IShopcartReadOnly extends IReadOnlyRepo<ShopcartView, UUID> {
}
