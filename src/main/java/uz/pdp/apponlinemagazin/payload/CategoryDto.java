package uz.pdp.apponlinemagazin.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryDto {

    private Integer id;

    @NotNull(message = "{CATEGORY_NAME}")
    private String name;

    private Integer parentCategoryId;

    private Integer order;
}
