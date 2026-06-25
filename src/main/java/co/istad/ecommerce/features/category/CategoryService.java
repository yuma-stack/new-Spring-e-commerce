package co.istad.ecommerce.features.category;

import co.istad.ecommerce.features.category.dto.CategoryResponse;
import co.istad.ecommerce.features.category.dto.CreateCategoryRequest;
import co.istad.ecommerce.features.category.dto.RequestDto;
import co.istad.ecommerce.features.category.dto.UpdateCategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface CategoryService {

    //get all category by pagination
    Page<CategoryResponse> getAllCategory(int pageNumber, int pageSize);

    CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest updateCategoryRequest);

    CategoryResponse getCategoryById(Integer id);

    void hardDeleteCategoryById(Integer id);

    void softDeleteCategoryById(Integer id);

    List<CategoryResponse> getSubCategories(Integer parentCategoryId);

    CategoryResponse createNew(CreateCategoryRequest createCategoryRequest);

    //filter specification
    Page<CategoryResponse> findByCriteria(RequestDto requestDto, Pageable pageable);

}
