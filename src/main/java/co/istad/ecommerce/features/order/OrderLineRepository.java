package co.istad.ecommerce.features.order;

import org.springframework.data.jpa.repository.JpaRepository;
                                              //Entity or Table Name, DataType of PK
public interface OrderLineRepository extends JpaRepository<Order.OrderLine, Integer> {
}
