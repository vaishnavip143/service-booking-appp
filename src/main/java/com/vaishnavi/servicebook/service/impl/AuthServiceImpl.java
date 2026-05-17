package com.vaishnavi.servicebook.service.impl;

import com.vaishnavi.servicebook.dto.ApiResponse;
import com.vaishnavi.servicebook.dto.LoginDto;
import com.vaishnavi.servicebook.dto.RegisterDto;
import com.vaishnavi.servicebook.security.JwtUtil;
import com.vaishnavi.servicebook.service.AuthService;
import com.vaishnavi.servicebook.userentity.ProviderProfile;
import com.vaishnavi.servicebook.userentity.User;
import com.vaishnavi.servicebook.userentity.UserType;
import com.vaishnavi.servicebook.repository.ProviderProfileRepository;
import com.vaishnavi.servicebook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final ProviderProfileRepository providerRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @Override
    public ApiResponse<?> register(RegisterDto dto) {
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            return ApiResponse.error("Email already exists");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .userType(UserType.valueOf(dto.getUserType()))
                .build();

        User savedUser = userRepo.save(user);

        if (savedUser.getUserType() == UserType.PROVIDER) {
            ProviderProfile profile = ProviderProfile.builder()
                    .businessName(dto.getBusinessName())
                    .address(dto.getAddress())
                    .profileCompletion(0)
                    .profileCompleted(false)
                    .verified(false)
                    .user(savedUser)
                    .build();
            providerRepo.save(profile);
        }
        return ApiResponse.success(null, "User registered successfully");
    }

    @Override
    public ApiResponse<?> login(LoginDto dto) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        } catch (Exception e) {
            return ApiResponse.error("Invalid credentials");
        }

        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getUserType().name());

        Map<String, Object> responseData = new java.util.HashMap<>(Map.of(
                "token", token,
                "id", user.getId(),
                "userType", user.getUserType(),
                "name", user.getName(),
                "email", user.getEmail()));

        if (user.getUserType() == UserType.PROVIDER) {
            providerRepo.findByUser(user).ifPresent(profile -> responseData.put("providerProfileId", profile.getId()));
        }

        return ApiResponse.success(responseData, "Login successful");
    }
}
