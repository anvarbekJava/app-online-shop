package uz.pdp.apponlinemagazin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.apponlinemagazin.domain.Role;
import uz.pdp.apponlinemagazin.domain.Users;
import uz.pdp.apponlinemagazin.payload.ApiResponse;
import uz.pdp.apponlinemagazin.payload.PasswordDto;
import uz.pdp.apponlinemagazin.payload.RegisterDto;
import uz.pdp.apponlinemagazin.payload.UserDto;
import uz.pdp.apponlinemagazin.repository.RoleRepository;
import uz.pdp.apponlinemagazin.repository.UsersRepository;
import uz.pdp.apponlinemagazin.service.UserService;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse add(RegisterDto dto) {
        if (dto.getPassword().equals(dto.getPrePassword())){
            if (usersRepository.existsByPhoneNumber(dto.getPhoneNumber()))
                return new ApiResponse("Bunday user mavjud qayta kiriting", false);
            Users users  = new Users();
            Optional<Role> optionalRole = roleRepository.findById(dto.getRoleId());
            if (!optionalRole.isPresent())
                return new ApiResponse("Bunday role mavjud emas qayta tanlang", false);
            Role role = optionalRole.get();
            users.setFirstName(dto.getFirstName());
            users.setLastName(dto.getLastName());
            users.setPhoneNumber(dto.getPhoneNumber());
            users.setPassword(passwordEncoder.encode(dto.getPassword()));
            users.setRoles(role);
            usersRepository.save(users);

            return new ApiResponse("User saqlandi", true);
        }
        return new ApiResponse("Password va prePassword teng emas", false);
    }
   /// ROLe edet
    @Override
    public ApiResponse edetRole(Integer id, Integer roleId) {

        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (!optionalUsers.isPresent())
            return new ApiResponse("User topilmadi", false);

        Users users = optionalUsers.get();
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (!optionalRole.isPresent())
            return new ApiResponse("Role topilmadi", false);

        users.setRoles(optionalRole.get());
        usersRepository.save(users);
        return new ApiResponse("Role qoshildi", true);
    }

    @Override
    public ApiResponse edetUser(Integer id, UserDto dto) {
        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (!optionalUsers.isPresent())
            return new ApiResponse("User topilmadi", false);
        Users users = optionalUsers.get();
        users.setFirstName(dto.getFirstName());
        users.setLastName(dto.getLastName());
        users.setPhoneNumber(dto.getPhoneNumber());
        Optional<Role> optionalRole = roleRepository.findById(dto.getRoleId());
        if (!optionalRole.isPresent())
            return new ApiResponse("Role topilmadi", false);
        Role role = optionalRole.get();
        users.setRoles(role);
        usersRepository.save(users);
        return new ApiResponse("Edet user", true);
    }

    @Override
    public ApiResponse changePassword(Integer id, PasswordDto dto) {
        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (!optionalUsers.isPresent())
            return new ApiResponse("User topilmadi", false);
        Users users = optionalUsers.get();
        boolean matches = passwordEncoder.matches(dto.getBeforePassword(), users.getPassword());
        if(matches){
            if (dto.getPassword().equals(dto.getPrePassword())){
                users.setPassword(passwordEncoder.encode(dto.getPassword()));
                usersRepository.save(users);
            }else {
                return new ApiResponse("password and prePassword no equals", false);
            }
        }else {
            return new ApiResponse("beforePassword and password no equals", false);
        }
        return new ApiResponse("Change password", true);
    }

    @Override
    public List<UserDto> getAll() {
        List<Users> usersList = usersRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (Users users : usersList) {
           userDtoList.add(new UserDto(
                    users.getId(),
                    users.getFirstName(),
                    users.getLastName(),
                    null,
                    users.getPhoneNumber(),
                    users.getRoles(),
                    users.isEnabled()
            ));
        }

        return userDtoList;
    }

    @Override
    public UserDto get(Integer id) {
        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (!optionalUsers.isPresent())
            return new UserDto();
        Users users = optionalUsers.get();
        UserDto dto = new UserDto(
                users.getId(),
                users.getFirstName(),
                users.getLastName(),
                null,
                users.getPhoneNumber(),
                users.getRoles(),
                users.isEnabled()
        );
        return dto;
    }

    @Override
    public ApiResponse enabledUser(Integer id) {
        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (!optionalUsers.isPresent())
            return new ApiResponse("User not found", false);
        Users users = optionalUsers.get();
        if (users.isEnabled()) {
            users.setEnabled(false);
        }else {
            users.setEnabled(true);
        }
        usersRepository.save(users);
        return new ApiResponse("Enabled success", true);
    }

    @Override
    public ApiResponse delete(Integer id) {
        try {
            usersRepository.deleteById(id);
            return new ApiResponse("Deleted user", true);
        }catch (Exception e){
            return new ApiResponse("No deleted", false);
        }
    }
}
