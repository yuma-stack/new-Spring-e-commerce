package co.istad.sokkeang.ecommerce.service.Impl;

import co.istad.sokkeang.ecommerce.domain.Category;
import co.istad.sokkeang.ecommerce.dto.CategoryResponse;
import co.istad.sokkeang.ecommerce.dto.CreateCategoryRequest;
import co.istad.sokkeang.ecommerce.dto.UpdateCategoryRequest;
import co.istad.sokkeang.ecommerce.repository.CategoryRepository;
import co.istad.sokkeang.ecommerce.repository.ProductRepository;
import co.istad.sokkeang.ecommerce.service.CategoryService;
import co.istad.sokkeang.ecommerce.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j  //for log
@RequiredArgsConstructor   //this annotation (shortcut for code injection dependency)
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;



    @Override
    public CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest updateCategoryRequest) {
        Category category = categoryRepository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found with this id."));
        if(category.getIsDeleted()==true){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"This category is already deleted.");
        }
        if(updateCategoryRequest.name() !=null){
            category.setName(updateCategoryRequest.name());
        }
        if(updateCategoryRequest.description()!=null){
            category.setDescription(updateCategoryRequest.description());
        }
        if(updateCategoryRequest.icon()!=null){
            category.setIcon(updateCategoryRequest.icon());
        }
        category = categoryRepository.save(category);

        return categoryMapper.mapCategoryToCategoryResponse(category);
    }


    @Override
    public CategoryResponse getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::mapCategoryToCategoryResponse).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category not found with this id."));
    }


    @Override
    public void hardDeleteCategoryById(Integer id) {
        log.info("deleteCategoryById: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category ID not found"
                ));
        categoryRepository.delete(category);
    }

    @Override
    public void softDeleteCategoryById(Integer id) {
        Category category = categoryRepository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category ID not found"));
        category.setIsDeleted(true);
        for (Category c : category.getChildCategories()){
            c.setIsDeleted(true);
        }
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponse> getSubCategories(Integer parentCategoryId) {
        Category category = categoryRepository.findById(parentCategoryId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found with this id."));
        if(category.getIsDeleted()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"This category is already deleted.");
        }
        return categoryRepository.findCategoriesByParentCategory(category).stream()
                .map(categoryMapper::mapCategoryToCategoryResponse).toList();
    }


    @Override
    public Page<CategoryResponse> getAllCategory(int pageNumber, int pageSize) {
        log.info("getAllCategories with pageNumber: {}, pageSize: {} ", pageNumber, pageSize);
        //1.  setup page request
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        //2. find data in database
        Page<Category> categoryResponsePage = categoryRepository.findAll(pageable);

        return categoryResponsePage.map(categoryMapper::mapCategoryToCategoryResponse);
    }

    @Override
    public CategoryResponse createNew(CreateCategoryRequest createCategoryRequest) {
        log.info("createNew {}", createCategoryRequest);

        //validate category name
        boolean isExisting = categoryRepository.existsByName(createCategoryRequest.name());
        if (isExisting)
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Category has already been used"
            );
        Category parentCategory = null;
        CategoryResponse parentCategoryResponse = null;

        //validate parent category (mean ot)
        if (createCategoryRequest.parentCategoryId() != null) {
            parentCategory = categoryRepository.findById(createCategoryRequest.parentCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Parent category has not been found"
                    ));
//            parentCategoryResponse = CategoryResponse.builder()
//                    .id(parentCategory.getId())
//                    .name(parentCategory.getName())
//                    .description(parentCategory.getDescription())
//                    .icon(parentCategory.getIcon())
//                    .is_deleted(parentCategory.getIsDeleted())
//                    .build();
        }

        //DTO don't exist so it is a System generated dat
        Category category = categoryMapper.mapCreateCategoryRequestToCategory(createCategoryRequest);
        category.setIsDeleted(false);
        category.setParentCategory(parentCategory);

        //insert if primary key is null
        //update if primary key has value
        category = categoryRepository.save(category);

        return categoryMapper.mapCategoryToCategoryResponse(category);

        //map from domain to dto (so we cut it by using annotation Mapping)
//        return CategoryResponse.builder()
//                .id(category.getId())
//                .description(category.getDescription())
//                .icon(category.getIcon())
//                .is_deleted(category.getIsDeleted())
//                .parentCategory(parentCategoryResponse)
//                .build();
    }

}
