package dev.pe.app.services.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service @RequiredArgsConstructor
public class OrderService {

  private final IOrderRepo orderRepoReadOnly;

  @Transactional
  public void create(UUID id) {

    // MEJORAR EL PROCEDURE Y VERIFICAR ERRORES
    try {
      orderRepoReadOnly.generatePurchase(id);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }

  }
}
