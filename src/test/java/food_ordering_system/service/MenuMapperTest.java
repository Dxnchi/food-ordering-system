package food_ordering_system.service;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.entity.Category;
import food_ordering_system.entity.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MenuMapperTest {

    @InjectMocks
    private MenuServiceImpl menuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapToDto() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Burgers");

        Menu menu = new Menu();
        menu.setId(100L);
        menu.setName("Test Burger");
        menu.setDescription("Test Desc");
        menu.setPrice(new BigDecimal("50.00"));
        menu.setCategory(category);

        MenuDto resultDto = menuService.mapToDto(menu);

        assertNotNull(resultDto);
        assertEquals(100L, resultDto.getId());
        assertEquals("Test Burger", resultDto.getName());
        assertEquals(new BigDecimal("50.00"), resultDto.getPrice());
        assertEquals(1L, resultDto.getCategoryId());
        assertEquals("Burgers", resultDto.getCategoryName());
    }
}