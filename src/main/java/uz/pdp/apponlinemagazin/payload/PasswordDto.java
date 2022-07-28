package uz.pdp.apponlinemagazin.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PasswordDto {

    @NotBlank(message = "{PASSWORD}")
    @Size(min = 8, max = 12, message = "{PASSOWD_LENGTH}")
    private String beforePassword;

    @NotBlank(message = "{PASSWORD}")
    @Size(min = 8, max = 12, message = "{PASSOWD_LENGTH}")
    private String password;

    @NotBlank(message = "{PASSWORD}")
    @Size(min = 8, max = 12, message = "{PASSOWD_LENGTH}")
    private String prePassword;
}
