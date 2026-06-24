package food_ordering_system.repository;

import food_ordering_system.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    // 1. Filter by just the category
    Page<Menu> findByCategoryId(Long categoryId, Pageable pageable);

    // 2. Filter by just the search term (case-insensitive)
    Page<Menu> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // 3. Filter by BOTH category and search term
    Page<Menu> findByCategoryIdAndNameContainingIgnoreCase(Long categoryId, String name, Pageable pageable);
}