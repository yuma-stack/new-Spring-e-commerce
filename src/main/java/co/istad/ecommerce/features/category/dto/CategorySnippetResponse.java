package co.istad.ecommerce.features.category.dto;

import lombok.Builder;

@Builder

//create this for summery to use in Product response
// which mean it show only some filed when we get product and see 2 filed of category
public record CategorySnippetResponse(
        Integer id,
        String name
) {
}
