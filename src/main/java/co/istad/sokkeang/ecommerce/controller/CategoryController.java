package co.istad.sokkeang.ecommerce.controller;

import co.istad.sokkeang.ecommerce.dto.CategoryResponse;
import co.istad.sokkeang.ecommerce.dto.CreateCategoryRequest;
import co.istad.sokkeang.ecommerce.dto.UpdateCategoryRequest;
import co.istad.sokkeang.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    //already add annotation
//  public CategoryController(CategoryService categoryService){
//      this.categoryService = categoryService;
//  }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createNew(@Valid @RequestBody CreateCategoryRequest createCategoryRequest){
        return categoryService.createNew(createCategoryRequest);
    }

    @GetMapping
    public Page<CategoryResponse> getAllCategories(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize
    ){
        log.info("pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        return categoryService.getAllCategory(pageNumber, pageSize);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Integer id){
        log.info("deleteCategoryById: {}", id);
        categoryService.hardDeleteCategoryById(id);
    }


    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Integer id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteCategory(@PathVariable Integer id){
        categoryService.softDeleteCategoryById(id);
    }

    @GetMapping("/{parentCategoryId}/subcategories")
    public List<CategoryResponse> getAllSubCategories(@PathVariable Integer parentCategoryId){
        return categoryService.getSubCategories(parentCategoryId);
    }

    @PatchMapping("/{id}")
    public CategoryResponse updateCategoryById(@PathVariable Integer id, @RequestBody UpdateCategoryRequest updateCategoryRequest){
        return  categoryService.updateCategoryById(id,updateCategoryRequest);
    }








}
