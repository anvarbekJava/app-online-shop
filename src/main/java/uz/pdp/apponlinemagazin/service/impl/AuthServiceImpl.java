package uz.pdp.apponlinemagazin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.apponlinemagazin.domain.Users;
import uz.pdp.apponlinemagazin.payload.ApiResponse;
import uz.pdp.apponlinemagazin.payload.LoginDto;
import uz.pdp.apponlinemagazin.payload.UserDto;
import uz.pdp.apponlinemagazin.repository.UsersRepository;
import uz.pdp.apponlinemagazin.security.JwtProvider;
import uz.pdp.apponlinemagazin.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public ApiResponse login(LoginDto dto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getPhoneNumber(), dto.getPassword()));
            Users users = (Users) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(dto.getPhoneNumber());
            UserDto userSend = new UserDto(
                    users.getFirstName(),
                    users.getLastName(),
                    users.getPhoneNumber(),
                    users.getRoles()
            );
            return new ApiResponse("Token", true, token, userSend);
        }catch (BadCredentialsException badCredentialsException){
            return new ApiResponse("Login yoki parol xato", false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return usersRepository.findByPhoneNumber(username).orElseThrow(() -> new UsernameNotFoundException("phonNumber"));
    }
}
