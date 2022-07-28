package uz.pdp.apponlinemagazin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.apponlinemagazin.domain.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);

    @Query(value = "Select *\n" +
            "from category\n" +
            "where enabled is true\n" +
            "and parent_category_id is null\n" +
            "order by ordered asc", nativeQuery = true)
    List<Category> getMainCategoryOrderedAndEnabledTrue();
}
