package uz.pdp.apponlinemagazin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.apponlinemagazin.domain.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query(value = "Select * from customer where status =:status\n" +
            "                         and order_time between :startDate and :endDate", nativeQuery = true)
    List<Customer> getCustomerByStatusAndBeetwenOrderTime(
            @Param(value = "status") String status, @Param(value = "startDate") Long startDate, @Param(value = "endDate") Long endDate);

}
