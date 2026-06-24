package food_ordering_system.service;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.entity.Category;
import food_ordering_system.entity.Menu;
import food_ordering_system.exception.CategoryNotFoundException;
import food_ordering_system.repository.CategoryRepository;
import food_ordering_system.repository.MenuRepository;
import food_ordering_system.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Response<MenuDto> createMenu(MenuDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + dto.getCategoryId()));

        Menu menu = mapToEntity(dto, category);
        Menu savedMenu = menuRepository.save(menu);
        return Response.success("Menu created successfully", mapToDto(savedMenu));
    }

    @Override
    public Response<Page<MenuDto>> getAllMenus(Long categoryId, String search, int page, int size, String sort) {
        // 1. Parse the sort string (e.g., "price,asc" or "name,desc")
        Sort.Direction direction = Sort.Direction.ASC;
        String sortBy = "id"; // Default sort

        if (sort != null && sort.contains(",")) {
            String[] sortParams = sort.split(",");
            sortBy = sortParams[0];
            if (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")) {
                direction = Sort.Direction.DESC;
            }
        }

        // 2. Create the Pageable object
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // 3. Decide which repository method to call based on what filters were provided
        Page<Menu> menuPage;
        if (categoryId != null && search != null && !search.isEmpty()) {
            menuPage = menuRepository.findByCategoryIdAndNameContainingIgnoreCase(categoryId, search, pageable);
        } else if (categoryId != null) {
            menuPage = menuRepository.findByCategoryId(categoryId, pageable);
        } else if (search != null && !search.isEmpty()) {
            menuPage = menuRepository.findByNameContainingIgnoreCase(search, pageable);
        } else {
            menuPage = menuRepository.findAll(pageable);
        }

        // 4. Map the Page<Menu> to Page<MenuDto>
        Page<MenuDto> dtoPage = menuPage.map(this::mapToDto);
        return Response.success("Menus retrieved successfully", dtoPage);
    }

    @Override
    public Response<MenuDto> getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));
        return Response.success("Menu retrieved successfully", mapToDto(menu));
    }

    @Override
    public Response<MenuDto> updateMenu(Long id, MenuDto dto) {
        // Verify menu exists
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));

        // Verify the new category exists
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + dto.getCategoryId()));

        // Update fields
        existingMenu.setName(dto.getName());
        existingMenu.setDescription(dto.getDescription());
        existingMenu.setPrice(dto.getPrice());
        existingMenu.setImageUrl(dto.getImageUrl());
        existingMenu.setCategory(category);

        Menu updatedMenu = menuRepository.save(existingMenu);
        return Response.success("Menu updated successfully", mapToDto(updatedMenu));
    }

    @Override
    public Response<Void> deleteMenu(Long id) {
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));

        menuRepository.delete(existingMenu);
        return Response.success("Menu deleted successfully", null);
    }

    // --- PRIVATE MAPPER METHODS ---

    private MenuDto mapToDto(Menu menu) {
        MenuDto dto = new MenuDto();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setDescription(menu.getDescription());
        dto.setPrice(menu.getPrice());
        dto.setImageUrl(menu.getImageUrl());
        dto.setCategoryId(menu.getCategory().getId());
        dto.setCategoryName(menu.getCategory().getName());
        return dto;
    }

    private Menu mapToEntity(MenuDto dto, Category category) {
        Menu menu = new Menu();
        menu.setName(dto.getName());
        menu.setDescription(dto.getDescription());
        menu.setPrice(dto.getPrice());
        menu.setImageUrl(dto.getImageUrl());
        menu.setCategory(category);
        return menu;
    }
}