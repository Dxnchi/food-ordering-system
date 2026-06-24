package food_ordering_system.service;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.response.Response;
import org.springframework.data.domain.Page;

public interface MenuService {
    Response<MenuDto> createMenu(MenuDto dto);

    // Upgraded for Week 2: Pagination & Filtering
    Response<Page<MenuDto>> getAllMenus(Long categoryId, String search, int page, int size, String sort);

    Response<MenuDto> getMenuById(Long id);

    // Added for Week 2: Update and Delete
    Response<MenuDto> updateMenu(Long id, MenuDto dto);
    Response<Void> deleteMenu(Long id);
}