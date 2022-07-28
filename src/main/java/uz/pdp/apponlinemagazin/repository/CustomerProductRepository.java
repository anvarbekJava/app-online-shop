package uz.pdp.apponlinemagazin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apponlinemagazin.domain.CustomerProduct;

public interface CustomerProductRepository extends JpaRepository<CustomerProduct, String> {
}
