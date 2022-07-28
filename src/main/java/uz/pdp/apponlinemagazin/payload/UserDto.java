package uz.pdp.apponlinemagazin.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.apponlinemagazin.domain.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private Integer roleId;

    @NotBlank(message = "{PHONE_NUMBER}")
    @Pattern(regexp = "^[+][0-9]{9,15}$", message = "{PHONE_NUMBER_PATTERN}")
    private String phoneNumber;


    private Role roleList;

    private boolean enabled;

    public UserDto(String firstName, String lastName, String phoneNumber, Role roleList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.roleList = roleList;
    }
}
