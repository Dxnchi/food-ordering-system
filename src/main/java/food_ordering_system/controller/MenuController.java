package food_ordering_system.controller;

import food_ordering_system.dto.CategoryDto;
import food_ordering_system.dto.MenuDto;
import food_ordering_system.response.Response;
import food_ordering_system.service.MenuService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@Validated
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<Response<MenuDto>> create(@RequestBody @Valid MenuDto dto) {
        return ResponseEntity.ok(menuService.createMenu(dto));
    }

    @GetMapping
    public ResponseEntity<Response<Page<MenuDto>>> all(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size must be at least 1") int size,
            @RequestParam(defaultValue = "id,asc") String sort
    ) {
        return ResponseEntity.ok(menuService.getAllMenus(categoryId, search, page, size, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<MenuDto>> byId(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<MenuDto>> update(@PathVariable Long id, @RequestBody @Valid MenuDto dto) {
        return ResponseEntity.ok(menuService.updateMenu(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.deleteMenu(id));
    }

    @GetMapping("/{id}/category")
    public ResponseEntity<Response<CategoryDto>> getMenuCategory(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuCategory(id));
    }
}