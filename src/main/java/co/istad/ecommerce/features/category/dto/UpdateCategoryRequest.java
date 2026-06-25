package co.istad.ecommerce.features.category.dto;

public record UpdateCategoryRequest(
        String name,
        String description,
        String icon
) {
}
