package uz.pdp.apponlinemagazin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.apponlinemagazin.domain.Users;

import java.util.Optional;
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Users> findByPhoneNumber(String phoneNumber);
}
