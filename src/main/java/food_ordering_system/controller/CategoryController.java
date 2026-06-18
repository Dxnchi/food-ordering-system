package food_ordering_system.controller;

import food_ordering_system.dto.CategoryDto;
import food_ordering_system.response.Response;
import food_ordering_system.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Response<List<CategoryDto>>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(Response.success("Categories retrieved successfully", categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CategoryDto>> getCategoryById(@PathVariable Long id) {
        CategoryDto dto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(Response.success("Category retrieved successfully", dto));
    }

    @PostMapping
    public ResponseEntity<Response<CategoryDto>> addCategory(@RequestBody @Valid CategoryDto dto) {
        CategoryDto createdDto = categoryService.addCategory(dto);
        // Using HTTP 201 Created status inside the generic Response, though 200 is passed locally via the wrapper builder logic
        Response<CategoryDto> responseBody = Response.<CategoryDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Category created successfully")
                .data(createdDto)
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<CategoryDto>> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDto dto) {
        CategoryDto updatedDto = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(Response.success("Category updated successfully", updatedDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        // Returning 200 OK with null data so the JSON wrapper displays properly
        return ResponseEntity.ok(Response.success("Category deleted successfully", null));
    }
}