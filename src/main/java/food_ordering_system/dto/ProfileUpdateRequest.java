package food_ordering_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileUpdateRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    private String address;
}