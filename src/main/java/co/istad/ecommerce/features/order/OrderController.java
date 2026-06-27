package co.istad.ecommerce.features.order;

import co.istad.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.ecommerce.features.order.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vi/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponse createNew(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        return  orderService.createNew(createOrderRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<OrderResponse> findAll(
            @RequestParam (defaultValue = "o") int pageNumber,
            @RequestParam (defaultValue = "25") int pageSize

    ){
        return orderService.findAll(pageNumber, pageSize);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable UUID id){
        return orderService.findById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}/soft-delete")
    public void softDelete (@PathVariable UUID id){
        orderService.softDelete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void hardDelete (@PathVariable UUID id){
        orderService.hardDelete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/payment")
    public OrderResponse paymentById (@PathVariable UUID id){
        return  orderService.paymentById(id);
    }

}
