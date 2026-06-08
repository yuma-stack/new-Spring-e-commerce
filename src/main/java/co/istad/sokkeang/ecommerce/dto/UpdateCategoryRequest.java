package co.istad.sokkeang.ecommerce.dto;

public record UpdateCategoryRequest(
        String name,
        String description,
        String icon
) {
}
