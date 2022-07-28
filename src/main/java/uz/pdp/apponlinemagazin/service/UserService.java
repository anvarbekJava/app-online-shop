package uz.pdp.apponlinemagazin.service;

import uz.pdp.apponlinemagazin.payload.ApiResponse;
import uz.pdp.apponlinemagazin.payload.PasswordDto;
import uz.pdp.apponlinemagazin.payload.RegisterDto;
import uz.pdp.apponlinemagazin.payload.UserDto;

import java.util.List;

public interface UserService {
    ApiResponse add(RegisterDto dto);

    ApiResponse edetRole(Integer id, Integer roleId);

    ApiResponse edetUser(Integer id, UserDto dto);

    ApiResponse changePassword(Integer id, PasswordDto dto);

    List<UserDto> getAll();

    UserDto get(Integer id);

    ApiResponse enabledUser(Integer id);

    ApiResponse delete(Integer id);
}
