package uz.pdp.apponlinemagazin.payload;

import lombok.Data;
import uz.pdp.apponlinemagazin.domain.enums.CustomerStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;

@Data
public class CustomerDto {
    private String id;

    @NotNull(message = "{FULL_NAME}")
    private String fullName;

    @NotBlank(message = "{PHONE_NUMBER}")
    @Pattern(regexp = "^[+][0-9]{9,15}$", message = "{PHONE_NUMBER_PATTERN}")
    private String phoneNumber;

    private String telegramNumber;

    private boolean paymentType;

    private Timestamp createdAt;

    private String status;

    private List<CustomerProductDto> customerProductDtoList;

    private Double totalCount;

    private Double totalAmount;
}
