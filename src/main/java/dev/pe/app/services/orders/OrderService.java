package dev.pe.app.services.orders;

import dev.pe.app.domain.utils.PageableUtil;
import dev.pe.app.domain.utils.responses.DataResponse;
import dev.pe.app.domain.utils.responses.PageableResponse;
import dev.pe.app.models.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service @RequiredArgsConstructor
public class OrderService {

  private final IOrderRepo orderRepoReadOnly;

  public DataResponse<Order> create(UUID id) {
    try {
      orderRepoReadOnly.generatePurchase(id);
      return DataResponse
          .<Order>builder()
          .status(HttpStatus.CREATED.value())
          .message("order created successful")
          .build();
    } catch (Exception ex) {

      var message = ex.getMessage().split("\n")[0];
      var index = message.indexOf("[ERROR: ") + 8;
      var error = message.substring(index);

      return DataResponse
          .<Order>builder()
          .message("error generating order")
          .error(error)
          .status(HttpStatus.BAD_REQUEST.value())
          .build();
    }
  }

  public PageableResponse<Order> getOrders(Pageable pageable, UUID id) {
   var pageOfOrders = orderRepoReadOnly.findAllByIdUserBuyer(pageable, id);

   var prev = PageableUtil.getPrevPage(pageOfOrders);
   var next = PageableUtil.getNextPage(pageOfOrders);

    if(pageOfOrders.getNumber() >= pageOfOrders.getTotalPages() & pageOfOrders.getTotalPages() != 0) {
      return PageableResponse
          .<Order>builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .error(
              "No content page, see that the page is less than " + pageOfOrders.getTotalPages()
          )
          .build();
    }

   return PageableResponse
       .<Order>builder()
       .data(pageOfOrders.getContent())
       .prev(prev)
       .next(next)
       .page(pageOfOrders.getNumber())
       .pageSize(pageOfOrders.getSize())
       .hints(pageOfOrders.getTotalElements())
       .totalPages(pageOfOrders.getTotalPages())
       .status(HttpStatus.OK.value())
       .build();
  }


}
