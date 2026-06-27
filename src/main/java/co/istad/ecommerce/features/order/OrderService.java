package co.istad.ecommerce.features.order;

import co.istad.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.ecommerce.features.order.dto.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface OrderService {

    OrderResponse createNew(CreateOrderRequest orderRequest);

    Page<OrderResponse> findAll (int pageNumber, int pageSize);

    OrderResponse findById (UUID id);

    void softDelete (UUID id);

    void hardDelete (UUID id);

    OrderResponse paymentById (UUID id);

}
