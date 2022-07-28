package uz.pdp.apponlinemagazin.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginDto {

    @NotBlank(message = "{PHONE_NUMBER}")
    @Pattern(regexp = "^[+][0-9]{9,15}$", message = "{PHONE_NUMBER_PATTERN}")
    private String phoneNumber;

    @NotNull(message = "{PASSWORD}")
    @Size(min = 8, max = 12, message = "{PASSOWR_LENGTH}")
    private String password;
}
