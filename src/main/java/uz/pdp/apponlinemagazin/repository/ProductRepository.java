package uz.pdp.apponlinemagazin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.apponlinemagazin.domain.Products;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Integer> {
    @Query(value = "Select * from products pr " +
            "join category c on pr.category_id = c.id " +
            "where c.parent_category_id =:categoryId and pr.enabled == true",
            nativeQuery = true)
    List<Products> getProductByCategory(@Param("categoryId") Integer categoryId);

   List<Products> findAllByCategory_ParentCategoryIdAndEnabledTrue(Integer category_parentCategory_id);
//
//    List<Products> findAllByCategory_Id(Integer category_id);

}
