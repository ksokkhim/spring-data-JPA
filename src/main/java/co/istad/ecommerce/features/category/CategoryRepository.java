package co.istad.ecommerce.features.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {
    boolean existsByName(String name);

    List<Category> getCategoryByParentCategoryId(Integer id);

//    @Query("SELECT n FROM Category n WHERE n.parentCategory.name =?1")
//    List<Category> findByCategoryName(String name);

    @Query("SELECT n FROM Category n WHERE LOWER( n.parentCategory.name) LIKE %?#{[0].toLowerCase()}%")
    List<Category> findByCategoryNameContains(String name);

}

