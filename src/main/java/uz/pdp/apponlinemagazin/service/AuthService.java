package uz.pdp.apponlinemagazin.service;

import uz.pdp.apponlinemagazin.payload.ApiResponse;
import uz.pdp.apponlinemagazin.payload.LoginDto;

public interface AuthService {
    ApiResponse login(LoginDto dto);
}
