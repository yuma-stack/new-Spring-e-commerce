package co.istad.ecommerce.features.category.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RequestDto {
    private List<SearchRequestDto> searchRequestDto;
    private GlobalOperator globalOperator;
    public static enum GlobalOperator{
        AND,OR
    }
}