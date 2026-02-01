package com.vaishnavi.servicebook.controller;

import com.vaishnavi.servicebook.Userentity.ProviderProfile;
import com.vaishnavi.servicebook.Userentity.User;
import com.vaishnavi.servicebook.Userentity.UserType;
import com.vaishnavi.servicebook.dto.LoginRequestDto;
import com.vaishnavi.servicebook.dto.LoginResponseDto;
import com.vaishnavi.servicebook.dto.RegisterDto;
import com.vaishnavi.servicebook.repository.ProviderProfileRepository;
import com.vaishnavi.servicebook.repository.UserRepository;
import com.vaishnavi.servicebook.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final ProviderProfileRepository providerRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepo,
                          ProviderProfileRepository providerRepo,
                          BCryptPasswordEncoder passwordEncoder,
    JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.providerRepo = providerRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUserType(UserType.valueOf(dto.getUserType().toUpperCase()));

        // ✅ If user is a provider, create ProviderProfile and link both sides
        if ("PROVIDER".equalsIgnoreCase(dto.getUserType())) {
            ProviderProfile providerProfile = new ProviderProfile();
            providerProfile.setBusinessName(dto.getBusinessName());
            providerProfile.setAddress(dto.getAddress());

            // Link both sides
            providerProfile.setUser(user);
            user.setProviderProfile(providerProfile);
        }

        userRepo.save(user); // CascadeType.ALL will save ProviderProfile too
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {

        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user);

        LoginResponseDto response = new LoginResponseDto(
                token,
                user.getId(),
                user.getUserType().name()
        );

        return ResponseEntity.ok(response);
    }

}

