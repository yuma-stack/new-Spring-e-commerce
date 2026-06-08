package co.istad.sokkeang.ecommerce.mapper;

import co.istad.sokkeang.ecommerce.domain.Category;

import co.istad.sokkeang.ecommerce.dto.CategoryResponse;
import co.istad.sokkeang.ecommerce.dto.CreateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    //return type = target
    //parameter = source



    Category mapCreateCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest);

    @Mapping(source = "parentCategory.id", target = "parentId")
    CategoryResponse mapCategoryToCategoryResponse(Category category);



//    //Transfer from dto to domain model (so we cut it by using annotation Mapping and write without body)
//    public Category mapCreateCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest){
//        Category category = new Category();
//        category.setName(createCategoryRequest.name());
//        category.setDescription(createCategoryRequest.description());
//        category.setIcon(createCategoryRequest.icon());
//       return category;
//    }



}
