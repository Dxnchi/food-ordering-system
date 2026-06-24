package food_ordering_system.service;

import food_ordering_system.dto.AuthResponse;
import food_ordering_system.dto.LoginRequest;
import food_ordering_system.dto.RegisterRequest;
import food_ordering_system.entity.Role;
import food_ordering_system.entity.User;
import food_ordering_system.repository.RoleRepository;
import food_ordering_system.repository.UserRepository;
import food_ordering_system.response.Response;
import food_ordering_system.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public Response<String> register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        Role customerRole = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Default role CUSTOMER not found"));

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // BCrypt Hash!
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.getRoles().add(customerRole);

        userRepository.save(user);

        return Response.success("User registered successfully", null);
    }

    public Response<AuthResponse> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials"); // Same message to avoid revealing info
        }

        if (!user.isActive()) {
            throw new IllegalArgumentException("Account inactive. Please contact support.");
        }

        String token = jwtUtils.generateToken(user.getEmail());
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        AuthResponse authResponse = new AuthResponse(token, user.getEmail(), user.getName(), roles);
        return Response.success("Login successful", authResponse);
    }
}