package food_ordering_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String refreshToken; // <-- Added this
    private String email;
    private String name;
    private List<String> roles;
}