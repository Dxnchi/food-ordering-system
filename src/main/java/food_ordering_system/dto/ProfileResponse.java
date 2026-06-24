package food_ordering_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}