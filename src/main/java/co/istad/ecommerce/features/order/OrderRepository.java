package co.istad.ecommerce.features.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
                                       //Entity or Table Name, DataType of PK
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
