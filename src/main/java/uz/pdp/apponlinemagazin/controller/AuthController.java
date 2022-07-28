package uz.pdp.apponlinemagazin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apponlinemagazin.domain.Users;
import uz.pdp.apponlinemagazin.payload.ApiResponse;
import uz.pdp.apponlinemagazin.payload.LoginDto;
import uz.pdp.apponlinemagazin.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto dto){
        ApiResponse apiResponse = authService.login(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }



}
