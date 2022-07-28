package uz.pdp.apponlinemagazin.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class RegisterDto {
    @NotNull(message = "{FIRSTNAME}")
    private String firstName;

    @NotNull(message = "{LASTNAME}")
    private String lastName;

    @NotBlank(message = "{PHONE_NUMBER}")
    @Pattern(regexp = "^[+][0-9]{9,15}$", message = "{PHONE_NUMBER_PATTERN}")
    private String phoneNumber;


    @NotNull(message = "{PASSWORD}")
    @Size(min = 8, max = 12, message = "{PASSOWD_LENGTH}")
    private String password;

    @NotNull(message = "{PASSWORD}")
    @Size(min = 8, max = 12, message = "{PASSOWD_LENGTH}")
    private String prePassword;


    @NotNull(message = "{ROLE_ID}")
    private Integer roleId;
}
