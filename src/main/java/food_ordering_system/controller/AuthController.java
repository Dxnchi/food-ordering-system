package food_ordering_system.controller;

import food_ordering_system.dto.*;
import food_ordering_system.response.Response;
import food_ordering_system.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for registering, logging in, and managing user profiles")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new customer")
    @PostMapping("/register")
    public ResponseEntity<Response<String>> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Login and get JWT tokens")
    @PostMapping("/login")
    public ResponseEntity<Response<AuthResponse>> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Get a new access token using a refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<Response<AuthResponse>> refreshToken(@RequestBody @Valid TokenRefreshRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @Operation(summary = "Get currently logged-in user's profile")
    @GetMapping("/me")
    public ResponseEntity<Response<ProfileResponse>> getMe(Authentication authentication) {
        // authentication.getName() automatically extracts the email from the SecurityContext!
        return ResponseEntity.ok(authService.getMe(authentication.getName()));
    }

    @Operation(summary = "Update currently logged-in user's profile")
    @PutMapping("/me")
    public ResponseEntity<Response<ProfileResponse>> updateMe(Authentication authentication, @RequestBody @Valid ProfileUpdateRequest request) {
        return ResponseEntity.ok(authService.updateMe(authentication.getName(), request));
    }
}