package co.istad.ecommerce.features.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends  JpaRepository<Product, Integer> {
    
    Optional<Product> findByCode(String code);

    Boolean existsByName(String name);

}
