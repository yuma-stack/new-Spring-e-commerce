package co.istad.ecommerce.features.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 255) //for English is Khmer use text
        String name,
        @Size(max = 500)
        String description,
        @Size(max = 255)
        String thumbnail,
        @NotNull(message = "Unit price is required")
        @Min(0)
        BigDecimal unitPrice,

        @NotNull(message = "Qty is required")
        @Min(0) //count from 0, at least 0
        Integer qty,
        @NotNull
        @Positive //count from 1
        Integer categoryId

) {
}
