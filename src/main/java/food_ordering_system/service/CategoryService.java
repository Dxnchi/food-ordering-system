package food_ordering_system.service;

import food_ordering_system.dto.CategoryDto;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
}