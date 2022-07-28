package uz.pdp.apponlinemagazin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apponlinemagazin.domain.Users;
import uz.pdp.apponlinemagazin.payload.ApiResponse;
import uz.pdp.apponlinemagazin.payload.PasswordDto;
import uz.pdp.apponlinemagazin.payload.RegisterDto;
import uz.pdp.apponlinemagazin.payload.UserDto;
import uz.pdp.apponlinemagazin.service.UserService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody RegisterDto dto){
        ApiResponse apiResponse = userService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    @PutMapping("/edetRole/{id}")
    public HttpEntity<?> edetRole(@PathVariable Integer id, @RequestParam Integer roleId){
        ApiResponse apiResponse = userService.edetRole(id, roleId);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    @PutMapping("/edetUser/{id}")
    public HttpEntity<?> edetUser(@PathVariable Integer id, @RequestBody UserDto dto){
        ApiResponse apiResponse = userService.edetUser(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    @PutMapping("/changePassword/{id}")
    public HttpEntity<?> changePassword(@PathVariable Integer id, @RequestBody PasswordDto dto){
        ApiResponse apiResponse = userService.changePassword(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    @GetMapping
    public HttpEntity<?> get(){
        List<UserDto> dtoList = userService.getAll();
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id){
        UserDto dto = userService.get(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    @PutMapping("/enabledUser/{id}")
    public HttpEntity<?> enabledUser(@PathVariable Integer id){
        ApiResponse apiResponse = userService.enabledUser(id);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse  = userService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    @GetMapping("/example")
    public HttpEntity<?> getUserAuthentication(){
        String firstName = Users.getUsers().getFirstName();
        return ResponseEntity.ok(firstName);
    }
}
