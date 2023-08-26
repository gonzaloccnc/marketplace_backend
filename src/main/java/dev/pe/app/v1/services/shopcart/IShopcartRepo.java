package dev.pe.app.v1.services.shopcart;

import dev.pe.app.v1.models.shopcart.Shopcart;
import dev.pe.app.v1.models.shopcart.ShopcartKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShopcartRepo extends JpaRepository<Shopcart, ShopcartKey> {
}
