package uz.pdp.apponlinemagazin.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CustomerProductDto {

    private String id;

    @NotNull(message = "{PRODUCT_ID}")
    private Integer productId;

    private String productName;

    @NotNull(message = "{PRODUCT_PROPERTIES_COUNT}")
    private Integer count;

    private Double amount;

    private Double totalPrice;
}
