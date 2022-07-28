package uz.pdp.apponlinemagazin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apponlinemagazin.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
