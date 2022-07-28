package uz.pdp.apponlinemagazin.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductDto {

    @NotNull(message = "{NAME_PRODUCT}")
    private String name;

    @NotNull(message = "{CATEGORY_ID}")
    private Integer categoryId;


    @NotNull(message = "{PRODUCT_SIZE}")
    private String size;

    //NAQD PUL DA SOTISH NARXI
    @NotNull(message = "{CASH_PRICE}")
    private Double cashPrice;

    @NotNull(message = "{TRANSFER_PRICE}")
    private Double transferPrice;

    private String attachmentId;

    private List<ProductPropertiesDto> propertiesList;

    private boolean enabled;


}
