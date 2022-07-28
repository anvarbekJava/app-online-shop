package uz.pdp.apponlinemagazin.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.apponlinemagazin.domain.Role;
import uz.pdp.apponlinemagazin.domain.Users;
import uz.pdp.apponlinemagazin.domain.enums.RoleName;
import uz.pdp.apponlinemagazin.repository.RoleRepository;
import uz.pdp.apponlinemagazin.repository.UsersRepository;

import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initialMode;
    @Override
    public void run(String... args) throws Exception {
            if (initialMode.equals("always")){

                Role admin = roleRepository.save(new Role(RoleName.ROLE_ADMIN));
                roleRepository.save(new Role(RoleName.ROLE_DIRECTOR));
                roleRepository.save(new Role(RoleName.ROLE_OPERATOR));
                roleRepository.save(new Role(RoleName.ROLE_MODERATOR));
                
               Users users = new Users(
                        "Alijon",
                        "Valijonov",
                        "+998975027300",
                        passwordEncoder.encode("adminadmin"),
                        admin
                );

                usersRepository.save(users);
            }
    }
}
