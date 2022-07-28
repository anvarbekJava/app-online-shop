package uz.pdp.apponlinemagazin.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductPropertiesDto {

    private Integer id;

    @NotNull(message = "{PRODUCT_KEY}")
    private String key;

    @NotNull(message = "{PRODUCT_VALUE}")
    private String value;

}
