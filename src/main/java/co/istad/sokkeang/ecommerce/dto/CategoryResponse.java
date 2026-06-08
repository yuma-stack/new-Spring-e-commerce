package co.istad.sokkeang.ecommerce.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Integer id,
        String name,
        String description,
        String icon,
        Boolean is_deleted,
        Integer parentId
) {
}
