package co.istad.sokkeang.ecommerce.service;

import co.istad.sokkeang.ecommerce.dto.CategoryResponse;
import co.istad.sokkeang.ecommerce.dto.CreateCategoryRequest;
import co.istad.sokkeang.ecommerce.dto.UpdateCategoryRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest updateCategoryRequest);

    CategoryResponse getCategoryById(Integer id);

    void hardDeleteCategoryById(Integer id);

    void softDeleteCategoryById(Integer id);

    List<CategoryResponse> getSubCategories(Integer parentCategoryId);

    Page<CategoryResponse> getAllCategory(int pageNumber, int pageSize);

    CategoryResponse createNew(CreateCategoryRequest createCategoryRequest);



}
