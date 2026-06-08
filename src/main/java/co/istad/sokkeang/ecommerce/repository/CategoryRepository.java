package co.istad.sokkeang.ecommerce.repository;

import co.istad.sokkeang.ecommerce.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository //optional for JpaRepository
                                             //Entity or Table Name, DataType of PK
public interface CategoryRepository extends JpaRepository<Category, Integer>{

    boolean existsByName(String name); //check category



    List<Category> findAllByIsDeletedAndParentCategory(Boolean isDeleted, Category parentCategory);

    List<Category> findCategoriesByParentCategory(Category category);
}

