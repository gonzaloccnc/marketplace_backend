package dev.pe.app.services.shopcart;

import dev.pe.app.models.shopcart.Shopcart;
import dev.pe.app.models.shopcart.ShopcartKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IShopcartRepo extends JpaRepository<Shopcart, ShopcartKey> {
}
