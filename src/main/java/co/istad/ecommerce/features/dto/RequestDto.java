package co.istad.ecommerce.features.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RequestDto {

    private List<SearchRequestDto> searchRequestDto;
    private GlobalOperator globalOperator;
    public static enum GlobalOperator{
        AND,OR
    }
}
