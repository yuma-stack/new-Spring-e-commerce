package co.istad.ecommerce.features.order;

import co.istad.ecommerce.features.order.dto.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse mapOrderToOrderResponse(Order order);

}
