package co.istad.ecommerce.features.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository //optional for JpaRepository
                                             //Entity or Table Name, DataType of PK
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {

    boolean existsByName(String name); //check category


    List<Category> findCategoriesByParentCategory(Category category);

    Page<Category> findAll(Specification<Category> searchSpecification, Pageable pageable);
}

