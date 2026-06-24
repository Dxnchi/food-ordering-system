package food_ordering_system.service;

import food_ordering_system.dto.*;
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
        user.setPassword(passwordEncoder.encode(request.getPassword()));
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
            throw new IllegalArgumentException("Invalid credentials");
        }
        if (!user.isActive()) {
            throw new IllegalArgumentException("Account inactive. Please contact support.");
        }

        String token = jwtUtils.generateToken(user.getEmail());
        String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return Response.success("Login successful", new AuthResponse(token, refreshToken, user.getEmail(), user.getName(), roles));
    }

    public Response<AuthResponse> refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        if (jwtUtils.validateToken(requestRefreshToken)) {
            String email = jwtUtils.extractEmail(requestRefreshToken);
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

            String newToken = jwtUtils.generateToken(user.getEmail());
            String newRefreshToken = jwtUtils.generateRefreshToken(user.getEmail());
            List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

            return Response.success("Token refreshed", new AuthResponse(newToken, newRefreshToken, user.getEmail(), user.getName(), roles));
        }
        throw new IllegalArgumentException("Invalid refresh token");
    }

    public Response<ProfileResponse> getMe(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return Response.success("Profile retrieved", new ProfileResponse(user.getName(), user.getEmail(), user.getPhoneNumber(), user.getAddress()));
    }

    public Response<ProfileResponse> updateMe(String email, ProfileUpdateRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        userRepository.save(user);
        return Response.success("Profile updated", new ProfileResponse(user.getName(), user.getEmail(), user.getPhoneNumber(), user.getAddress()));
    }
}